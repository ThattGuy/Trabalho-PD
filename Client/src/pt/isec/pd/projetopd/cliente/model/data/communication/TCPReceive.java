package pt.isec.pd.projetopd.cliente.model.data.communication;

import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TCPReceive extends Thread {
    private MessageReceivedListener listener;
    private Socket socket;

    public TCPReceive(Socket socket, MessageReceivedListener listener) {
        this.socket = socket;
        this.listener = listener;
    }

    @Override
    public void run() {
        Socket socket = null;
        ObjectInputStream objectInputStream = null;

        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            while (true) {
                Object receivedObject = objectInputStream.readObject();

                if (listener != null) {
                    listener.onMessageReceived(receivedObject);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            String errorMessage = "Error receiving serializable object: " + e.getMessage();
            System.err.println(errorMessage);
            if (listener != null) {
                listener.onMessageReceived(errorMessage);
            }
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                String errorMessage = "Error closing the socket: " + e.getMessage();
                System.err.println(errorMessage);
                if (listener != null) {
                    listener.onMessageReceived(errorMessage);
                }
            }
        }
    }

    public interface MessageReceivedListener {
        void onMessageReceived(Object message);
    }
}
