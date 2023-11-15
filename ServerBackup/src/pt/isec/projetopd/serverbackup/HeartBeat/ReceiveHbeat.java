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
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public ReceiveHbeat(MulticastSocket ms) {
        this.ms = ms;
    }

    //TODO: Update databse version


    @Override
    public void run() {
        try {
            while (true) {
                ms.setSoTimeout(30000);
                DatagramPacket dp = new DatagramPacket(new byte[256],256);
                ms.receive(dp);


                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(dp.getData(), 0, dp.getLength()));
                Object o = ois.readObject();

                if(o instanceof HbeatMessage)
                {
                    HbeatMessage info = (HbeatMessage) o;
                    System.out.println("Received heartbeat from " + info.getRMI() + " " + info.getRegistryPort());
                    //TODO: Update databse version
                }
                else{
                    System.out.println("Received unknown object");
                }
            }
        }
        catch ( IOException e ) {
            System.out.println("[ERROR]: TIMEOUT: No heartbeat received");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

