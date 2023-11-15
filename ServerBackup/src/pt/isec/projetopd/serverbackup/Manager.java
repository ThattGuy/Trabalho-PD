package pt.isec.projetopd.serverbackup;

import pt.isec.projetopd.serverbackup.HeartBeat.ReceiveHbeat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

public class Manager {

    private final int MULTICAST_PORT = 4444; //Porto de escuta multicast
    private final String MULTICAST_ADDRESS = "230.40.40.40"; //Endereço multicast
    private final String DBpath;
    private MulticastSocket multicast;

    public Manager(String path)
    {
        try {
        DBpath = path;
        this.multicast = new MulticastSocket(MULTICAST_PORT);
        this.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start(){
        try
        {
            this.multicast = new MulticastSocket(MULTICAST_PORT);
            InetAddress ipGroup = InetAddress.getByName(MULTICAST_ADDRESS);
            NetworkInterface nif = NetworkInterface.getByInetAddress(ipGroup);
            this.multicast.joinGroup(new InetSocketAddress(ipGroup, MULTICAST_PORT), nif);
            ReceiveHbeat receiveHbeat = new ReceiveHbeat(this.multicast);
            receiveHbeat.start();


        }
        catch (Exception e)
        {
            System.err.println("Error: " + e);
        }
    }
}
