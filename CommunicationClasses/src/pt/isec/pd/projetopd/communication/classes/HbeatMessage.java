package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;

public class HbeatMessage implements Serializable {
    private final String RMI;
    private final int REGISTRY_PORT;
    private int databaseVersion;

    public HbeatMessage(String rmi, int registryPort)//, String databasePath)
    {
        this.RMI = rmi;
        this.REGISTRY_PORT = registryPort;
        this.databaseVersion = 0;
    }
    public String getRMI() {
        return RMI;
    }
    public int getRegistryPort() {
        return REGISTRY_PORT;
    }


    public void updateDBVersion(int version){
        this.databaseVersion = version;
    }
}
