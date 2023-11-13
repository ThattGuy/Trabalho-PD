package pt.isec.pd.projetopd.server;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;

public class NotificationThread  extends Thread {

    ArrayList<ObjectOutputStream> clients = new ArrayList<>();
    private ServerSocket CliSocket = null;
    public NotificationThread(int port)
    {
        try {
            CliSocket = new ServerSocket(port);
        }catch (Exception e)
        {
            System.err.println("Error: " + e);
        }
    }

    public void sendNotifications(Object o){

        for(ObjectOutputStream client : clients){
            try{
                client.writeObject(o);
                client.flush();
                client.reset();
            }catch (Exception e){
                System.err.println("Error: " + e);
            }
        }
    }

    @Override
    public void run() {
        for (; ; ) {
            try {
                ObjectOutputStream nextClient = new ObjectOutputStream(CliSocket.accept().getOutputStream()); //Aceita um novo cliente

                System.out.println("Received client on notification " + nextClient.toString());

                clients.add(nextClient);
            } catch (Exception e) {
                System.out.println("O cliente nao entrou!");
                System.err.println("Error: " + e);
            }
        }
    }
}
