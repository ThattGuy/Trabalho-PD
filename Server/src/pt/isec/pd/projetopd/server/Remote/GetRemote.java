package pt.isec.pd.projetopd.server.Remote;

import pt.isec.pd.projetopd.server.data.DataBase.DataBase;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GetRemote extends UnicastRemoteObject implements UpdateDB{

    private DataBase dbManager;

    protected GetRemote(DataBase db) throws RemoteException {
        this.dbManager = db;
    }

    @Override
    public void getDB() throws RemoteException {

    }
}
