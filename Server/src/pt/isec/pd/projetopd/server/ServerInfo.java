package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.server.HeartBeat.SendHBeat;
import pt.isec.pd.projetopd.server.data.Authentication;

import java.net.Socket;
import java.util.ArrayList;

public class ServerInfo {
    private ArrayList<Socket> clientsList;
    private int nTCPConnections;
    private int databaseVersion;
    private SendHBeat sendHBeat;


    public ServerInfo(SendHBeat sendHBeat) {
        this.clientsList = new ArrayList<>();
        this.nTCPConnections = 0;
        this.sendHBeat = sendHBeat;
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

    public void updateDB(Object o) {
        this.databaseVersion++;
        this.sendHBeat.SendHeartBeat(databaseVersion);

        if(o instanceof Authentication) {
            Authentication auth = (Authentication) o;
            System.out.println("Received Authentication");
            System.out.println( auth.getUsername() + ": " + auth.getPassword());

        }



        //Atraves do comando enviar para uma classe que lide com ele e atualize a BD
    }

}
