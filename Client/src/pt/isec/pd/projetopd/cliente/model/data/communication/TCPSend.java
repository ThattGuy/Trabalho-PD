package pt.isec.pd.projetopd.cliente.model.data.communication;

import pt.isec.pd.projetopd.cliente.model.data.Data;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
            out = new ObjectOutputStream(socket.getOutputStream());
            this.socket = new Socket(ip, port);
        } catch (IOException e) {
            data.setLastMessage("Error connecting to Server: " + e);
            throw new RuntimeException(e);
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


