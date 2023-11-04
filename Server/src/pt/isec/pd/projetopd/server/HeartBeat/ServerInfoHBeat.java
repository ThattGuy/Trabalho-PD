package pt.isec.pd.projetopd.server.HeartBeat;

import java.io.Serializable;

public class ServerInfoHBeat implements Serializable {
    private final String RMI;
    private final int REGISTRY_PORT;
    private final String DATABASE_PATH;
    private int databaseVersion;

    public ServerInfoHBeat ( String rmi, int registryPort, String databasePath)
    {
        this.RMI = rmi;
        this.REGISTRY_PORT = registryPort;
        DATABASE_PATH = databasePath;
        this.databaseVersion = 0;
    }

    public void updateDBVersion(int version){
        this.databaseVersion = version;
    }
}
