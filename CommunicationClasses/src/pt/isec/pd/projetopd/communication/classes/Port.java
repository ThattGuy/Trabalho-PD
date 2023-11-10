package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;

public class Port implements Serializable {
    String port;
    public Port(String p){ this.port = p;}
    public String getPort(){ return this.port;}
}
