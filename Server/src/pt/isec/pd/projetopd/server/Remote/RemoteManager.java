package pt.isec.pd.projetopd.server.Remote;

import pt.isec.pd.projetopd.server.data.DataBase.DataBase;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RemoteManager {

    UpdateDB updateDB;
    Registry registry;
    GetRemote getRemote;
    int port_registry;
    DataBaseCopy dbRemote;
    public RemoteManager(DataBase db, int port) throws RemoteException {
        super();
        try{
            this.dbRemote= new DataBaseCopy(db.getDatabaseCopy()) ;
            this.getRemote = new GetRemote(db);
            this.start();
            this.port_registry = port;
        }catch (Exception e){}

    }
    private void start()
    {
        try {
            this.registry = LocateRegistry.createRegistry(port_registry);
            //this.registry.rebind("GetDB", this.updateDB);
            this.dbRemote = (DataBaseCopy) UnicastRemoteObject.exportObject(this.getRemote, 0);

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

}
