package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.server.HeartBeat.SendHBeat;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceiveTCPClients extends Thread {

    private ServerSocket CliSocket = null;
    private HandleRequests handleRequests;
    private ThreadGroup threadsClients;
      public ReceiveTCPClients(int port, HandleRequests handleRequests)
      {
          try {
              this.handleRequests = handleRequests;
              CliSocket = new ServerSocket(port);
              this.threadsClients = new ThreadGroup("TCP Clients");
          }catch(RuntimeException s){

          }
          catch (Exception e)
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

           new Thread(this.threadsClients, new HandleClient(nextClient, handleRequests)).start();

        }
        catch (Exception e)
        {
            System.out.println("O cliente nao entrou!");
            System.err.println("Error: " + e);
        }
    }

}
