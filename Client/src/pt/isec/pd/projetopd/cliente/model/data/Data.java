package pt.isec.pd.projetopd.cliente.model.data;

import pt.isec.pd.projetopd.cliente.model.data.communication.TCPSend;

import java.io.Serializable;

public class Data {
    private static String ip = null;
    private static int port = 0;
    private TCPSend tcpSend;

    public Data(String ip, int port) {
        Data.ip = ip;
        Data.port = port;
    }

    public void startTcpSend() {
        this.tcpSend = new TCPSend(ip, port);
        tcpSend.start();
    }

    public void sendToServer(Serializable objectToSend){
        tcpSend.setDataToSend(objectToSend);
    }

    public void setClientInfo() {

    }
}
