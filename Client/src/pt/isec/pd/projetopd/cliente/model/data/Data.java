package pt.isec.pd.projetopd.cliente.model.data;

import pt.isec.pd.projetopd.communication.classes.*;

import pt.isec.pd.projetopd.cliente.model.data.communication.TCPSend;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Data {
    private TCPSend tcpSend;
    private User clientInfo;
    private Socket socket;
    private String message = null;
    private String serverIP;
    private List<Event> events = new ArrayList<>();
    private List<Presence> presences = new ArrayList<>();
    public Data(String ip, int port) {;
        this.serverIP = ip;
        try {
            this.socket = new Socket(ip, port);
            this.tcpSend = new TCPSend(this);
        } catch (IOException e) {
            this.message = "Error creating socket: " + e.getMessage();
        }
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void sendToServer(Serializable objectToSend){
        tcpSend.sendObject(objectToSend);
    }

    public synchronized void setClientInfo(User clientInfo) {
        this.clientInfo = clientInfo;
    }
    public boolean isUserAdmin() {
        return clientInfo.isAdmin();
    }

    public synchronized void setMessage(String errorMessage) {
        this.message = errorMessage;
    }

    public synchronized String getMessage() {
        return message;
    }

    public synchronized void addEvents(Event event) {
        events.add(event);
    }

    public synchronized String getEventsString() {
        StringBuilder sb = new StringBuilder();

        for (Event event : events) {
            sb.append(event.toString());
            sb.append("\n");
        }
        //todo fix get null

        return sb.toString();
    }

    public synchronized void addPresence(Presence presence) {
        presences.add(presence);
    }

    public synchronized String getPresenceString() {
        StringBuilder sb = new StringBuilder();

        for (Presence presence : presences) {
            sb.append(presence.toString());
            sb.append("\n");
        }
        //todo fix get null

        return sb.toString();
    }

    public String getServerIP() {
        return this.serverIP;
    }
}
