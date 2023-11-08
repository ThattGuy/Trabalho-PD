package pt.isec.pd.projetopd.cliente.model.data;

import pt.isec.pd.projetopd.communication.classes.*;

import pt.isec.pd.projetopd.cliente.model.data.communication.TCPSend;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class Data {
    private static String ip = null;
    private static int port = 0;
    private TCPSend tcpSend;
    private User clientInfo;
    private Socket socket;
    private String message = null;
    public Data(String ip, int port) {
        Data.ip = ip;
        Data.port = port;

        try {
            this.socket = new Socket(ip, port);
            this.tcpSend = new TCPSend(this);
        } catch (IOException e) {
            this.message = "Error creating socket: " + e.getMessage();
        }

        tcpSend = new TCPSend(this);
    }

    public void sendToServer(Serializable objectToSend){
        tcpSend.sendObject(objectToSend);
    }

    public void setClientInfo(User clientInfo) {
        this.clientInfo = clientInfo;
    }
    public boolean isUserAdmin() {
        return clientInfo.isAdmin();
    }

    public void setMessage(String errorMessage) {
        this.message = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public Socket getSocket() {
        return this.socket;
    }
}
