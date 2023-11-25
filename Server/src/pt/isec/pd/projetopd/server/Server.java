package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.server.HeartBeat.SendHBeat;
import pt.isec.pd.projetopd.communication.classes.HbeatMessage;
import pt.isec.pd.projetopd.server.Remote.RemoteManager;

import java.net.*;
import java.io.*;

public class Server
{
    private final String RMI;
    private final int REGISTRY_PORT;
    private final int TCP_PORT;
    private final String DATABASE_PATH;

    //todas estas variaveis servem para enviar heartbeats sempre que ha uma atualizacao

    private final int MULTICAST_PORT = 4444; //Porto de escuta multicast
    private final String MULTICAST_ADDRESS = "230.40.40.40"; //Endereço multicast
    private MulticastSocket socket;
    private InetAddress ipGroup = null;
    private NetworkInterface nif;


    /**
     * O construtor recebe como argumentos o porto de escuta UDP e
     * o caminho da diretoria da base de dados passados em linha de comando
     */

    public Server(int tcpPort, String path, String name, int regPort)
    {
        this.TCP_PORT = tcpPort;
        this.DATABASE_PATH = path;
        this.RMI = name;
        this.REGISTRY_PORT = regPort;
        try
        {
            this.ipGroup = InetAddress.getByName("230.44.44.44");//e.g., 127.0.0.1, 192.168.10.1, ...

            try
            {
                this.nif = NetworkInterface.getByInetAddress(ipGroup);
            }
            catch (SocketException | NullPointerException | SecurityException ex)
            {
                this.nif = NetworkInterface.getByName("lo"); //e.g., lo, eth0, wlan0, en0, ...
            }

            try
            {
                socket = new MulticastSocket(MULTICAST_PORT);
            }
            catch (IOException ioe)
            {
                System.err.println("Error: " + ioe);
            }

            socket.joinGroup(new InetSocketAddress(InetAddress.getByName(MULTICAST_ADDRESS), MULTICAST_PORT), nif);
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e);
        }

    }

    public int getPort()
    {
        return REGISTRY_PORT;
    }

    public InetAddress getIpGroup()
    {
        return ipGroup;
    }

    public MulticastSocket getMulticastSocket()
    {
        return socket;
    }



    public static void main(String[] args)
    {

       if (args.length != 4)
            System.err.println("Syntax <portUDP>");

        try {

            Server server = new Server(Integer.parseInt(args[0]), args[1], args[2], Integer.parseInt(args[3]));
            HbeatMessage hbeatMessage = new HbeatMessage("rmi://" + InetAddress.getLocalHost().getHostAddress() + "/"+ server.RMI, server.REGISTRY_PORT, 0, InetAddress.getLocalHost().getHostAddress());
            SendHBeat sendHBeat = new SendHBeat(server.socket, hbeatMessage, server.MULTICAST_ADDRESS, server.MULTICAST_PORT);


            NotificationThread notificationThread = new NotificationThread(7001);
            ServerInfo serverInfo = new ServerInfo(sendHBeat, notificationThread);
            HandleRequests handleRequests = new HandleRequests(args[1], serverInfo);
            ReceiveTCPClients recvClient = new ReceiveTCPClients(server.TCP_PORT, handleRequests);
            RemoteManager remoteManager = new RemoteManager(server.REGISTRY_PORT, server.DATABASE_PATH, "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/"+ server.RMI);




        //Informação Debug
        System.out.println("Estou à escuta no endereço IP: " + server.getIpGroup().getHostAddress());
        System.out.println("O meu porto é o: " + server.getPort());


        //Start
            sendHBeat.start();
            new Thread(recvClient).start();
            new Thread(notificationThread).start();

        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }


}