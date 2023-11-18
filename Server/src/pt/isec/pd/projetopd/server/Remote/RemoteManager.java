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
    public RemoteManager(DataBase db, int port) {
        super();
        try{
            DataBaseCopy copy = new DataBaseCopy(db.getDatabaseCopy()) ;
            this.getRemote = new GetRemote(copy);
            this.port_registry = port;
            this.start();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
    private void start()
    {
        try {
            this.registry = LocateRegistry.createRegistry(port_registry);
            //this.registry.rebind("GetDB", this.updateDB);
            this.getRemote = (GetRemote) UnicastRemoteObject.exportObject(this.getRemote, 0);

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

}
