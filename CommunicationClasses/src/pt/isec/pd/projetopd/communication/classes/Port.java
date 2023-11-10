package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;

public class Port implements Serializable {
    int port;
    public Port(int p){ this.port = p;}
    public int getPort(){ return this.port;}
}