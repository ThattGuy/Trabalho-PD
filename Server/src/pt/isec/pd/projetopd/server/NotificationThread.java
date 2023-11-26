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


    private ServerInfo serverInfo;
    private ServerSocket CliSocket = null;
    public NotificationThread(int port, ServerInfo serverInfo)
    {
        this.serverInfo = serverInfo;
        try {
            CliSocket = new ServerSocket(port);
        }catch (Exception e)
        {
            System.err.println("Error: " + e);
        }

    }

    @Override
    public void run() {
        for (; ; ) {
            try {
                Socket client = CliSocket.accept(); //Aceita um novo cliente
                serverInfo.addNotificationClient(client);

                System.out.println("Received client on notification ");

            }catch(SocketTimeoutException e) {

                System.out.println("No client entered!");
            }
            catch (Exception e) {
                System.out.println("ERROR: No client entered ");
            }
        }
    }
}
