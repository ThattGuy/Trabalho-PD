package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;
import java.net.InetAddress;

public class HbeatMessage implements Serializable {
    private final String RMI;
    private final int REGISTRY_PORT;
    private int databaseVersion;

    private String ip;

    public HbeatMessage(String rmi, int registryPort, int db, String ip)//, String databasePath)
    {
        this.RMI = rmi;
        this.REGISTRY_PORT = registryPort;
        this.databaseVersion = db;
        this.ip = ip;
    }
    public String getRMI() {
        return RMI;
    }
    public int getRegistryPort() {
        return REGISTRY_PORT;
    }
    public int getDatabaseVersion(){return this.databaseVersion;}
    public String getIp() {return ip;}


    public void updateDBVersion(int version){
        this.databaseVersion = version;
    }
}
