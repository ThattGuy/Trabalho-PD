package pt.isec.pd.projetopd.cliente.model.data.communication;

import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TCPReceive extends Thread {
    private MessageReceivedListener listener;
    private final String ip;
    private final int port;

    public TCPReceive(String ip, int port, MessageReceivedListener listener) {
        this.ip = ip;
        this.port = port;
        this.listener = listener;
    }

    @Override
    public void run() {
        Socket socket = null;

        try {
            socket = new Socket(InetAddress.getByName(ip), port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response;

            while ((response = reader.readLine()) != null) {
                if (listener != null) {
                    listener.onMessageReceived(response);
                }
            }
        } catch (IOException e) {
            String s = "Error connecting to Server TCPReceive: ";
            System.err.println(s + e);
            if (listener != null) {
                listener.onMessageReceived(s);
            }
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                String s = "Error closing the socket, TCPReceive: ";
                System.err.println(s + e);
                if (listener != null) {
                    listener.onMessageReceived(s);
                }
            }
        }
    }

    public interface MessageReceivedListener {
        void onMessageReceived(String message);
    }
}
