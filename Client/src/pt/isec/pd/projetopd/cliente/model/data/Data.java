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
    private User userInfo;
    private Socket socket;
    private String message = null;
    private List<Event> events = new ArrayList<>();
    private List<Presence> presences = new ArrayList<>();
    public Data(String ip, int port) {;
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

    public synchronized void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }
    public boolean isUserAdmin() {
        if(userInfo instanceof Admin){
            return true;
        }
        return false;
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
        //todo delete old presence
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

    public String getUserName() {
        if(userInfo != null){
            return userInfo.getUsername();
        }
        return null;
    }

    public String getName() {
        if(userInfo != null){
            return userInfo.getName();
        }
        return null;
    }

    public int getStudentNumber() {
        if(userInfo != null){
            return userInfo.getStudentNumber();
        }
        return 0;
    }

    public int getNIF() {
        if(userInfo != null){
            return userInfo.getNIF();
        }
        return 0;
    }

    public String getID() {
        if(userInfo != null){
            return userInfo.getId();
        }
        return null;
    }

    public String getAddress() {
        if(userInfo != null){
            return userInfo.getAddress();
        }
        return null;
    }
}
