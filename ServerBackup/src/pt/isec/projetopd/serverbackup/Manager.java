package pt.isec.projetopd.serverbackup;

import org.sqlite.core.DB;
import pt.isec.projetopd.serverbackup.HeartBeat.ReceiveHbeat;
import pt.isec.projetopd.serverbackup.RMI.HandleRmi;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

public class Manager {

    private final int MULTICAST_PORT = 4444; //Porto de escuta multicast
    private final String MULTICAST_ADDRESS = "230.40.40.40"; //Endereço multicast
    private final String DBpath;
    private final File file;
    private MulticastSocket multicast;
    private HandleRmi rmiHandler;

    public Manager(String path)
    {
        try {
            this.DBpath = path;
            this.file = new File(path);
            if(!checkPath())
               return;

            this.multicast = new MulticastSocket(MULTICAST_PORT);
            start();

        } catch (IOException e) {
            throw new IllegalStateException("EXIT: The directory is not valid.");
        }
    }


    public boolean checkPath()  {

        if (!file.exists())
            if(!file.mkdirs())
                return false;

        return file.isDirectory() && file.length() == 0 ;
    }
    private void setRMI() {
        String directo = null;
        try{
             directo = new File(file.getPath()+ File.separator + DBpath).getCanonicalPath();
            this.rmiHandler = new HandleRmi(directo, this.DBpath);
        }catch(IOException ex){
            System.out.println("Erro E/S - " + ex);
        }
    }

    public void start(){
        try
        {
            //Manage RMI:
            setRMI();

            this.multicast = new MulticastSocket(MULTICAST_PORT);
            InetAddress ipGroup = InetAddress.getByName(MULTICAST_ADDRESS);
            NetworkInterface nif = NetworkInterface.getByInetAddress(ipGroup);
            this.multicast.joinGroup(new InetSocketAddress(ipGroup, MULTICAST_PORT), nif);
            ReceiveHbeat receiveHbeat = new ReceiveHbeat(this.multicast, this.rmiHandler);
            receiveHbeat.start();


        }
        catch(RuntimeException | IOException e){
            throw new RuntimeException(e);
        }


        this.multicast.close();
    }
}
