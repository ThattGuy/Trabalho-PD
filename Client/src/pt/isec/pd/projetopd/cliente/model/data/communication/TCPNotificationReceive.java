package pt.isec.pd.projetopd.cliente.model.data.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class TCPNotificationReceive extends Thread {
    private NotificationReceivedListener listener;
    private Socket socket;

    public TCPNotificationReceive(Socket socket, NotificationReceivedListener listener) {
        this.socket = socket;
        this.listener = listener;
    }

    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;

        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            while (true) {
                Object receivedNotification = objectInputStream.readObject();

                if (listener != null) {
                    listener.onNotificationReceived(receivedNotification);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            String errorMessage = "Error receiving notification: " + e.getMessage();
            System.err.println(errorMessage);
            if (listener != null) {
                listener.onNotificationReceived(errorMessage);
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
                    listener.onNotificationReceived(errorMessage);
                }
            }
        }
    }

    public interface NotificationReceivedListener {
        void onNotificationReceived(Object notification);
    }
}

