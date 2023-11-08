package pt.isec.pd.projetopd.cliente.model.data;

import pt.isec.pd.projetopd.cliente.model.data.communication.User;
import pt.isec.pd.projetopd.cliente.model.data.communication.TCPSend;

import java.io.Serializable;

public class Data {
    private static String ip = null;
    private static int port = 0;
    private TCPSend tcpSend;
    private User clientInfo;


    private String errorMessage = null;
    public Data(String ip, int port) {
        Data.ip = ip;
        Data.port = port;
        tcpSend = new TCPSend(ip, port, this);
    }

    public void sendToServer(Serializable objectToSend){
        tcpSend.sendObject(objectToSend);
    }

    public void setClientInfo(User clientInfo) {
        this.clientInfo = clientInfo;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
