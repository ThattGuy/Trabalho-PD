package pt.isec.pd.projetopd.cliente.model.data;

import pt.isec.pd.projetopd.cliente.model.data.communication.TCPSend;

import java.io.Serializable;

public class ClientData {
    private static String ip = null;
    private static int port = 0;
    private TCPSend tcpSend;

    public ClientData(String ip, int port) {
        ClientData.ip = ip;
        ClientData.port = port;
    }

    public void startTcpSend() {
        this.tcpSend = new TCPSend(ip, port);
        tcpSend.start();
    }

    public void sendToServer(Serializable objectToSend){
        tcpSend.setDataToSend(objectToSend);
    }

}
