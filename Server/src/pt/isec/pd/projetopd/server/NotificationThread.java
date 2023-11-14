package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.communication.classes.ServerPort;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NotificationThread  extends Thread {

    Map<String,ObjectOutputStream> clients;
    private ServerSocket CliSocket = null;
    public NotificationThread(int port)
    {
        try {
            CliSocket = new ServerSocket(port);
        }catch (Exception e)
        {
            System.err.println("Error: " + e);
        }
        clients = new HashMap<>();
    }

    public void sendNotifications(String mail, Object o) //send notifications to all clients except the one that sent the request
    {
        for (Map.Entry<String, ObjectOutputStream> entry : clients.entrySet()) {

            if(!Objects.equals(entry.getKey(), mail)) {

                ObjectOutputStream out = entry.getValue();
                try {
                    out.writeObject(o);
                    out.flush();
                    out.reset();
                } catch (Exception e) {
                    System.err.println("Error: " + e);
                }

            }
        }
    }

    @Override
    public void run() {
        for (; ; ) {
            try {
                Socket client = CliSocket.accept(); //Aceita um novo cliente
                ObjectOutputStream nextClient = new ObjectOutputStream(client.getOutputStream());

                System.out.println("Received client on notification " + nextClient.toString());

                client.setSoTimeout(500);

                ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                Object o = in.readObject();

                if(o instanceof String) {
                    clients.put((String) o, nextClient);
                }
                else{
                    client.close();
                }

            }catch(SocketTimeoutException e) {
                System.out.println("No client entered!");
            }
            catch (Exception e) {
                System.out.println("ERROR: No client entered ");
            }
        }
    }
}
