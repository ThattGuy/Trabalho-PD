package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.server.HeartBeat.SendHBeat;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

public class ServerInfo {

    private Map<ObjectOutputStream, String> clientsLists;
    private ArrayList<ObjectOutputStream> clientsList;
    private int nTCPConnections;
    private int databaseVersion;
    private SendHBeat sendHBeat;
    private HandleRequests handleRequests;
    private NotificationThread notifications;


    public ServerInfo(String path,SendHBeat sendHBeat, NotificationThread notificationThread){
        this.clientsList = new ArrayList<>();
        this.nTCPConnections = 0;
        this.sendHBeat = sendHBeat;
        this.handleRequests = new HandleRequests(path);
        this.notifications = notificationThread;
    }

    public int getnTCPConnections() {
        return nTCPConnections;
    }
    public int getDBVersion() {
        return this.databaseVersion;
    }

    public void addClient(ObjectOutputStream client) {
        this.clientsList.add(client);
        //this.clientsLists.put(client, email);
        this.nTCPConnections++;
        //this.sendHBeat.SendHeartBeat(databaseVersion);
    }

    public Object updateDB(Object o) {
        return handleRequests.receive(o);
            //this.databaseVersion++;
           // this.sendHBeat.SendHeartBeat(databaseVersion);

    }

}
