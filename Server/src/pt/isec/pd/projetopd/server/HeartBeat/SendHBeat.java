package pt.isec.pd.projetopd.server.HeartBeat;

import pt.isec.pd.projetopd.communication.classes.HbeatMessage;

import java.net.*;
import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SendHBeat{

    public MulticastSocket socket;
    public HbeatMessage InfoHbeatSend;
    private static String MULTICAST_ADDRES;
    private static int MULTICAST_PORT;

    public SendHBeat(MulticastSocket socket, HbeatMessage InfoHbeatSend, String MULTICAST_ADDRESS, int MULTICAST_PORT) {
        this.socket = socket;
        this.InfoHbeatSend = InfoHbeatSend;
        this.MULTICAST_ADDRES = MULTICAST_ADDRESS;
        this.MULTICAST_PORT = MULTICAST_PORT;
    }

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void start() {
        scheduler.scheduleAtFixedRate(this::HeartBeat, 0, 10, TimeUnit.SECONDS);
    }

    public void stop() {
        scheduler.shutdownNow();
    }
    public void SendHeartBeat(int DBversion){
        InfoHbeatSend.updateDBVersion(DBversion);
        this.HeartBeat();
    }

    public void HeartBeat() {
        try {
            try {
                //Enviar um heartbeat para o grupo multicast com a informação do servidor
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                ObjectOutputStream ous = new ObjectOutputStream(bout);
                ous.writeObject(InfoHbeatSend);
                ous.flush();
                var packet = new DatagramPacket(bout.toByteArray(), bout.size(), InetAddress.getByName(MULTICAST_ADDRES), MULTICAST_PORT);
                socket.send(packet);
                System.out.println("Heartbeat sent");
            } catch (Exception e) {
                //e.printStackTrace();
                System.err.println("Error: " + e);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }
}

