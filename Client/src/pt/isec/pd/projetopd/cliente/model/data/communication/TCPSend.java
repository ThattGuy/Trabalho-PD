package pt.isec.pd.projetopd.cliente.model.data.communication;

import pt.isec.pd.projetopd.cliente.model.data.Data;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPSend extends Thread {
    private String ip;
    private int port;
    private Serializable objectToSend;

    private Data data;
    private Socket socket;

    public TCPSend(String ip, int port, Data data) {
        this.ip = ip;
        this.port = port;
        this.data = data;
        try{
            this.socket = new Socket(ip, port);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDataToSend(Serializable dataToSend) {
        this.objectToSend = dataToSend;
    }

    @Override
    public synchronized void run() {
        //Socket socket = null;
        ObjectOutputStream out = null;

        try {
           // socket = new Socket(InetAddress.getByName(ip), port);
            out = new ObjectOutputStream(socket.getOutputStream());

            // Send the data to the server
            out.writeObject(objectToSend);
            out.flush();
        } catch (IOException e) {
            data.setLastMessage("Error connecting to Server: " + e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                data.setLastMessage("Error connecting to Server: " + e);
            }
        }
    }
}


