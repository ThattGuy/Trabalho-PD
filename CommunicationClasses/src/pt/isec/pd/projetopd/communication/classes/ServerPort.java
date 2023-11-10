package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;

public class ServerPort implements Serializable {
    private int port;
    public ServerPort(int p) {
        this.port = p;
    }

    public int getPortNumber() {
        return this.port;
    }

}