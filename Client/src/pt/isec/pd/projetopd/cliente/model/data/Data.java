package pt.isec.pd.projetopd.cliente.model.data;

import pt.isec.pd.projetopd.cliente.model.data.communication.ClientInfo;
import pt.isec.pd.projetopd.cliente.model.data.communication.TCPSend;

import java.io.Serializable;

public class Data {
    private static String ip = null;
    private static int port = 0;
    private TCPSend tcpSend;

    private ClientInfo clientInfo;

    private String lastMessage;

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

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

}