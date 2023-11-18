package pt.isec.pd.projetopd.server.Remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface UpdateDB extends Remote{
    List<Map<String, Object>> getDB() throws RemoteException;

}
