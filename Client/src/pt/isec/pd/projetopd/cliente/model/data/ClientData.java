package pt.isec.pd.projetopd.cliente.model.data;

import pt.isec.pd.projetopd.cliente.model.data.communication.TCPSend;

public class ClientData {
    private String ip;
    private int port;
    TCPSend tcpSend;

    public ClientData(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void startTcpSend() {
        this.tcpSend = new TCPSend(ip, port);
    }

}
