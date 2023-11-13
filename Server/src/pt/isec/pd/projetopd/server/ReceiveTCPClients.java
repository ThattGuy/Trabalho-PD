package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.server.HeartBeat.SendHBeat;

import java.io.ObjectOutputStream;
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

            //ObjectOutputStream out = new ObjectOutputStream(nextClient.getOutputStream());
            //serverInfo.addClient(out);

            System.out.println("Received client on receivetcpClients " + nextClient.getInetAddress() + ":" + nextClient.getPort());


           new Thread(this.threadsClients, new HandleClient(nextClient, serverInfo)).start();


        }
        catch (Exception e)
        {
            System.out.println("O cliente nao entrou!");
            System.err.println("Error: " + e);
        }

    }

}
