package pt.isec.pd.projetopd.cliente.model.data.communication;

import pt.isec.pd.projetopd.communication.classes.ServerPort;

import java.io.*;
import java.net.Socket;

public class TCPReceive extends Thread {
    private static boolean hasServerSocket = false;
    private MessageReceivedListener listener;
    private Socket socket;
    private String serverIP;

    public TCPReceive(Socket socket, String serverIP, MessageReceivedListener listener) {
        this.socket = socket;
        this.listener = listener;
        this.serverIP = serverIP;
    }

    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;

        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            while (true) {
                Object receivedObject = objectInputStream.readObject();

                createNotificationThread(receivedObject);

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

    private void createNotificationThread(Object receivedObject) {
        if(!TCPReceive.hasServerSocket){
            if(receivedObject instanceof ServerPort serverPort){
                try {
                    Socket serverSocket = new Socket(this.serverIP, serverPort.getPortNumber());
                    new TCPReceive(serverSocket, this.serverIP, this.listener);
                    hasServerSocket = true;
                } catch (IOException e) {
                    String errorMessage =  "Error creating notificationThread: " + e.getMessage();
                    System.err.println(errorMessage);
                }
            }
        }
    }

    public interface MessageReceivedListener {
        void onMessageReceived(Object message);
    }
}
