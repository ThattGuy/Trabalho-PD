package pt.isec.projetopd.serverbackup.HeartBeat;

import pt.isec.pd.projetopd.communication.classes.HbeatMessage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReceiveHbeat extends Thread{
    private final MulticastSocket ms;

    public ReceiveHbeat(MulticastSocket ms) {
        this.ms = ms;
    }

    //TODO: Update databse version


    @Override
    public void run() {
        try {
            for(;;) {

                ms.setSoTimeout(30000);
                DatagramPacket dp = new DatagramPacket(new byte[256],256);
                ms.receive(dp);


                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(dp.getData(), 0, dp.getLength()));
                Object o = ois.readObject();

                if(o instanceof HbeatMessage info)
                {
                    if(((HbeatMessage) o).getDatabaseVersion() == 0)//TODO: Verifcar se versao BD local igual a que vem no hbeat
                        throw new IOException("Database version is 0");
                    System.out.println("Received heartbeat from " + info.getRMI() + " " + info.getRegistryPort());
                    //TODO: Update databse version
                }
                else{
                    System.out.println("Received unknown object");
                }
            }
        }
        catch ( IOException e ) {
            throw new IllegalStateException("EXIT: TIMEOUT: No heartbeat received");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("EXIT : Local database is not up to date");
        }
    }
}

