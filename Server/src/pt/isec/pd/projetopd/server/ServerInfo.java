package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.server.HeartBeat.SendHBeat;

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
    private ArrayList<Socket> notificationClients;


    public ServerInfo(SendHBeat sendHBeat){
        this.clientsList = new HashMap<>();
        this.nTCPConnections = 0;
        this.sendHBeat = sendHBeat;
        this.databaseVersion = 0;
        this.notificationClients = new ArrayList<>();
    }

    public void addNotificationClient(Socket socket){
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
        this.databaseVersion++;
        this.sendHBeat.SendHeartBeat(databaseVersion);
    }

    public void removeClient(String mail) {
        this.clientsList.remove(mail);
        this.nTCPConnections--;
        this.databaseVersion--;
        this.sendHBeat.SendHeartBeat(databaseVersion);
    }
    private void updateAllClientsViews(Object data) {

        Iterator<Socket> iterator = notificationClients.iterator();
        while (iterator.hasNext()) {
            Socket sock = iterator.next();
            try (ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream())) {
                out.writeObject(data);
                out.flush();
                out.reset();
            } catch (Exception e) {
                iterator.remove(); // Use the Iterator's remove method
            }
        }
    }


    public void sendNotification(Object data){
        this.databaseVersion++;
        this.sendHBeat.SendHeartBeat(databaseVersion);
        System.out.println("I sended a notification");

        //TODO: Atualizar backups atraves do rmi!!

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