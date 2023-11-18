package pt.isec.pd.projetopd.server.Remote;

import pt.isec.pd.projetopd.server.data.DataBase.DataBase;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

public class GetRemote extends UnicastRemoteObject implements UpdateDB{

    private List<Map<String, Object>> database;

    protected GetRemote(DataBaseCopy db) throws RemoteException {
        this.database = db.getDatabase();
    }

    @Override
    public List<Map<String, Object>> getDB() throws RemoteException {
        return this.database;
    }
}
