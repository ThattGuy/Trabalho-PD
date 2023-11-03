package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.server.HandleClient;

import java.net.ServerSocket;
import java.net.Socket;

public class ReceiveTCPClients extends Thread {

    private ServerSocket CliSocket = null;
      public ReceiveTCPClients(int port)
      {
          try {
              CliSocket = new ServerSocket(port);
          }catch (Exception e)
          {
              System.err.println("Error: " + e);
          }
      }

    @Override
    public void run() {
        try
        {
            ThreadGroup tcpServer = new ThreadGroup("TCP Clients");
            for (int i = 0; i < 3; ++i)
            {
                Socket nextClient = CliSocket.accept(); //Aceita um novo cliente

               // infoServer.incrementConnections(); //Incrementa o número de ligações TCP


                //Informação Debug
                System.out.println("Received request from " + nextClient.getInetAddress() + ":" + nextClient.getPort());

                new Thread(tcpServer, new HandleClient(nextClient)).start();
            }
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e);
        }
    }



}
