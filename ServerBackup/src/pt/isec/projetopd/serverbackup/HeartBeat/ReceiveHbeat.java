package pt.isec.projetopd.serverbackup.HeartBeat;

import pt.isec.pd.projetopd.communication.classes.HbeatMessage;
import pt.isec.projetopd.serverbackup.RMI.HandleRmi;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.MulticastSocket;


public class ReceiveHbeat extends Thread{
    private final MulticastSocket ms;
    private HandleRmi rmiHandler;

    public ReceiveHbeat(MulticastSocket ms, HandleRmi rmiHandler){
        this.ms = ms;
        this.rmiHandler = rmiHandler;
    }

    //TODO: Update databse version


    @Override
    public void run() {
        boolean first = true;
        try {
            for(;;) {

                ms.setSoTimeout(30000);
                DatagramPacket dp = new DatagramPacket(new byte[256],256);
                ms.receive(dp);


                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(dp.getData(), 0, dp.getLength()));
                Object o = ois.readObject();

                if(o instanceof HbeatMessage info)
                {
                    if(((HbeatMessage) o).getDatabaseVersion() != 0)//TODO: Verifcar se versao BD local igual a que vem no hbeat
                        throw new IOException("Database version is 0");
                    System.out.println("Received heartbeat from " + info.getRMI() + " " + info.getRegistryPort());
                    //TODO: Update databse version
                    if(first) {
                        rmiHandler.setLocalDatabase((HbeatMessage) o);
                        first = false;
                    }
                    else rmiHandler.fileReceived((HbeatMessage) o);
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

