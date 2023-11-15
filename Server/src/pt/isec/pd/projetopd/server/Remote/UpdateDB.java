package pt.isec.pd.projetopd.server.Remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UpdateDB extends Remote{
    void getDB() throws RemoteException;

}
