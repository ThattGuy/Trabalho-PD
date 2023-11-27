package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.server.HeartBeat.SendHBeat;
import pt.isec.pd.projetopd.server.Remote.RemoteManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ServerInfo {
    private Map<String, ObjectOutputStream> clientsList;
    private int nTCPConnections;
    private int databaseVersion;
    private SendHBeat sendHBeat;
    private ArrayList<ObjectOutputStream> notificationClients;
    private RemoteManager myRemote;


    public ServerInfo(SendHBeat sendHBeat, RemoteManager remote) {
        this.clientsList = new HashMap<>();
        this.nTCPConnections = 0;
        this.sendHBeat = sendHBeat;
        this.databaseVersion = 0;
        this.notificationClients = new ArrayList<>();
        this.myRemote = remote;
    }

    public void addNotificationClient(ObjectOutputStream socket){
        this.notificationClients.add(socket);
    }

    public int getnTCPConnections() {
        return nTCPConnections;
    }
    public int getDBVersion() {
        return this.databaseVersion;
    }

    public void addClient(String mail, ObjectOutputStream out) {
        this.clientsList.put(mail,out);
        this.nTCPConnections++;
        this.databaseVersion++; myRemote.setDatabaseVersion(databaseVersion);
        this.sendHBeat.SendHeartBeat(databaseVersion);
    }

    public void removeClient(String mail) {
        this.clientsList.remove(mail);
        this.nTCPConnections--;
        this.databaseVersion--;myRemote.setDatabaseVersion(databaseVersion);
        this.sendHBeat.SendHeartBeat(databaseVersion);
    }
    private void updateAllClientsViews(Object data) {

        Iterator<ObjectOutputStream> iterator = notificationClients.iterator();
        try{
            while (iterator.hasNext()) {
                ObjectOutputStream out = iterator.next();
                out.writeObject(data);
                out.flush();
                out.reset();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateBackup(Object data){
        this.databaseVersion++; myRemote.setDatabaseVersion(databaseVersion);
        this.sendHBeat.SendHeartBeat(databaseVersion);
        //TODO: Atualizar backups atraves do rmi!!
    }
    public void sendNotification(Object data){
        myRemote.sendNotification(data);
        updateAllClientsViews(data);

    }

    public String getClientMail(ObjectOutputStream out) {
        for (Map.Entry<String, ObjectOutputStream> entry : clientsList.entrySet()) {
            if(entry.getValue() == out) {
                return entry.getKey();
            }
        }
        return null;
    }

}