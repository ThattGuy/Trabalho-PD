package pt.isec.pd.projetopd.server.Remote;

import pt.isec.pd.projetopd.communication.interfaces.BackupServerInterface;
import pt.isec.pd.projetopd.server.ServerInfo;
import pt.isec.pd.projetopd.server.data.DataBase.DataBase;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RemoteManager {
    Registry registry;
    GetRemote getRemote;
    int port_registry;
    File directory;
    String file;
    String name; //vem da linha de comandos

    List<BackupServerInterface> backupServers;
    public RemoteManager( int port, String file, String name) {
        super();
        try{
            this.name = name;
            this.file = file;
            this.directory = new File(file.trim());
            backupServers = new ArrayList<>();
            if( !( directory.exists() ))// && directory.isDirectory() && directory.canRead() ))
                return;

            this.port_registry = port;
            this.start();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
    private void start()
    {
        System.setProperty("java.rmi.server.hostname", "localhost");

        try{
            try{

                this.registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

                System.out.println("Registry lancado!");

            }catch(RemoteException e){
                System.out.println("Registry provavelmente ja' em execucao!");
            }

            this.getRemote = new GetRemote(directory, 0, this);
            //this.getRemote = (GetRemote) UnicastRemoteObject.exportObject(this.getRemote, port_registry);


            Naming.bind(name, getRemote);

        }catch(RemoteException e){
            System.out.println("Erro remoto - " + e);
            System.exit(1);
        }catch(Exception e){
            System.out.println("Erro - " + e);
            System.exit(1);
        }
    }

    public void setDatabaseVersion(int databaseVersion){
        this.getRemote.setDatabaseVersion(databaseVersion);
    }

    public void sendNotification(Object data) {

       /* for(BackupServerInterface backup : backupServers){
            try {
                backup.updateDB(data);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        */
    }

}
