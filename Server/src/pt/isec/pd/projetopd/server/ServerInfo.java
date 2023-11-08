package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.server.HeartBeat.SendHBeat;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

public class ServerInfo {

    private Map<Socket, Integer> clientsLists;
    private ArrayList<Socket> clientsList;
    private int nTCPConnections;
    private int databaseVersion;
    private SendHBeat sendHBeat;
    private HandleRequests handleRequests;


    public ServerInfo(SendHBeat sendHBeat) {
        this.clientsList = new ArrayList<>();
        this.nTCPConnections = 0;
        this.sendHBeat = sendHBeat;
        this.handleRequests = new HandleRequests();
    }

    public int getnTCPConnections() {
        return nTCPConnections;
    }
    public int getDBVersion() {
        return this.databaseVersion;
    }

    public void addClient(Socket client) {
        this.clientsList.add(client);
        this.nTCPConnections++;
        this.sendHBeat.SendHeartBeat(databaseVersion);
    }

    public boolean updateDB(Object o) {
        if(handleRequests.receive(o)){
            this.databaseVersion++;
            this.sendHBeat.SendHeartBeat(databaseVersion);

            return true;
        }
        return false;
    }

}
