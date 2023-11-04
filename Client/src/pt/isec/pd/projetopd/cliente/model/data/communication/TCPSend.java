package pt.isec.pd.projetopd.cliente.model.data.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCPSend extends Thread {
    private String ip;
    private int port;
    private Object dataToSend;

    public TCPSend(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void setDataToSend(Object dataToSend) {
        this.dataToSend = dataToSend;
    }

    @Override
    public void run() {
        Socket socket = null;
        ObjectOutputStream out = null;

        try {
            socket = new Socket(InetAddress.getByName(ip), port);
            out = new ObjectOutputStream(socket.getOutputStream());

            // Send the data to the server
            out.writeObject(dataToSend);
            out.flush();
        } catch (IOException e) {
            System.err.println("Error connecting to Server: " + e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing resources: " + e);
            }
        }
    }
}


