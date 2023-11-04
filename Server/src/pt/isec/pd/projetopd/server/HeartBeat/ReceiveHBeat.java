package pt.isec.pd.projetopd.server.HeartBeat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class ReceiveHBeat implements Runnable{

    private MulticastSocket socket;

    public ReceiveHBeat(MulticastSocket socket) { this.socket = socket; }

    public void ReceiveHBeat()
    {
        byte[] buffer = new byte[4096];
        try {

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(packet.getData(), 0, packet.getLength()));
            Object o = ois.readObject();

            if(o instanceof ServerInfoHBeat)
            {
                ServerInfoHBeat info = (ServerInfoHBeat) o;
                //Update databse version
            }


        } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void run() {
        for (;;)
        {
            ReceiveHBeat();
        }
    }
}
