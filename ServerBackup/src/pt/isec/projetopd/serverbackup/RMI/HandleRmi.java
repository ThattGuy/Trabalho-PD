package pt.isec.projetopd.serverbackup.RMI;

import pt.isec.projetopd.serverbackup.BackupServerInterface;

import java.io.File;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HandleRmi  {

    File directory;
    public HandleRmi(File directory) throws RemoteException {

        super();
        this.directory = directory;
        start();
    }

    public void fileReceived(Object o) {
        System.out.println("Received file");
    }

    private void start(){
        try{

            try{
                LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
                System.out.println("Registry lancado!");
            }catch(RemoteException e){
                //Provavelmente ja tinha sido lancado.
            }

            /*
             * Cria o servico PartialPiValueService.
             */

            RemoteDatabase remoteDatabase = new RemoteDatabase();

            /*
             * Regista o servico com o nome "piWorker" para que os clientes possam encontra'-lo, ou seja,
             * obter a sua referencia remota.
             */

            Naming.bind("rmi://localhost/backup", remoteDatabase);

        }catch(RemoteException e){
            System.out.println("Erro remoto - " + e);
            System.exit(1);
        }catch(Exception e){
            System.out.println("Erro - " + e);
            System.exit(1);
        }

    }



}