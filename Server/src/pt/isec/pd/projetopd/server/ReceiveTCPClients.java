package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.server.HeartBeat.SendHBeat;

import java.net.ServerSocket;
import java.net.Socket;

public class ReceiveTCPClients extends Thread {

    private ServerSocket CliSocket = null;
    private ServerInfo serverInfo;
    private ThreadGroup threadsClients;
      public ReceiveTCPClients(int port,ServerInfo serverInfo)
      {
          try {
              this.serverInfo = serverInfo;
              CliSocket = new ServerSocket(port);
              this.threadsClients = new ThreadGroup("TCP Clients");
          }catch (Exception e)
          {
              System.err.println("Error: " + e);
          }
      }


    @Override
    public void run() {
    for (;;)
        try {
            Socket nextClient = CliSocket.accept(); //Aceita um novo cliente
            serverInfo.addClient(nextClient);

            System.out.println("Received request from " + nextClient.getInetAddress() + ":" + nextClient.getPort());

            new Thread(this.threadsClients, new HandleClient(nextClient, serverInfo)).start();

        }
        catch (Exception e)
        {
            System.err.println("Error: " + e);
        }

    }



}
