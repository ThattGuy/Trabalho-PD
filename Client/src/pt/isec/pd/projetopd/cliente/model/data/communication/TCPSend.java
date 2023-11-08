package pt.isec.pd.projetopd.cliente.model.data.communication;

import pt.isec.pd.projetopd.cliente.model.data.Data;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.Socket;

public class TCPSend {
    private String ip;
    private int port;
    private Data data;
    private Socket socket;

    private ObjectOutputStream out = null;

    public TCPSend(String ip, int port, Data data) {
        this.ip = ip;
        this.port = port;
        this.data = data;

        try {
            this.socket = new Socket(ip, port);
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (ConnectException e) {
            String errorMessage = "Error connecting to Server: " + e.getMessage();
            data.setLastMessage(errorMessage);
        } catch (IOException e) {
            String errorMessage = "Error connecting to Server: " + e.getMessage();
            data.setLastMessage(errorMessage);
        }
    }

    public void sendObject(Serializable objectToSend) {

        try {
            out.writeObject(objectToSend);
            out.flush();
        } catch (IOException e) {
            data.setLastMessage("Error sending object to Server: " + e);
            throw new RuntimeException(e);
        }

    }
}


