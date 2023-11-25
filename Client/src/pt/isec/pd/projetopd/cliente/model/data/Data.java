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
    private String presences = "No Presences";
    private int indexOfEventObject = -1;
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

    public synchronized void addEvents(EventList events) {
        this.events = events.getEvents();
    }

    public synchronized List<String> getEventsString() {
        List<String> sb = new ArrayList<>();

        for (Event event : events) {
            sb.add(event.toString());
        }

        return sb;
    }

    public synchronized void addPresences(PresencesList presencesList) {
        this.presences = presencesList.toString();
    }

    public synchronized String getPresenceString() {
        return presences;
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

    public void setEventIndexEdit(int index) {
        indexOfEventObject = index;
    }

    public Event getEventToEdit() {
        return events.get(indexOfEventObject);
    }

    public void logout() {
        userInfo = null;
        events = null;
        presences = null;
        tcpSend.sendObject(REQUESTS.LOGOUT);
    }

    public void addEventCode(RegisterCode registerCode) {
        events.get(indexOfEventObject).addPresenceCode(registerCode);
    }

    public void addEvent(Event event) {
        events.add(indexOfEventObject, event);
    }

    public RegisterCode getLastEventCode() {
        return events.get(indexOfEventObject).getPresenceCodes().getLast();
    }
}
