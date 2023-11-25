package pt.isec.projetopd.serverbackup.RMI;

import pt.isec.pd.projetopd.communication.classes.HbeatMessage;
import pt.isec.pd.projetopd.server.Remote.UpdateDB;

import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class HandleRmi  {

    String directory;
    String NameMyRmi = "rmi://localhost/backup";
    RemoteDatabase myRemoteService = null;
    public HandleRmi(String directory) throws RemoteException {

        super();
        this.directory = directory;
        StartmyRMI();
    }

    public void fileReceived(Object o) {
        System.out.println("Received file");
    }

    public void setLocalDatabase(HbeatMessage o) {

        //IR buscar a base de dados!


        try(FileOutputStream localFileOutputStream = new FileOutputStream(this.directory)){ //Cria o ficheiro local

            System.out.println("Ficheiro " + directory + " criado.");

            /*
             * Obtem a referencia remota para o servico do server.
             */
            UpdateDB serverDB = (UpdateDB) Naming.lookup(o.getRMI());


            /*
             * Passa ao servico RMI LOCAL uma referencia para o objecto localFileOutputStream.
             */
            myRemoteService.setFout(localFileOutputStream);

            /*
             * Obtem o ficheiro pretendido, invocando o metodo getFile no servico remoto.
             */

            serverDB.getFile(NameMyRmi, myRemoteService);

        }catch(RemoteException e){
            System.out.println("Erro remoto - " + e);
        }catch(NotBoundException e){
            System.out.println("Servico remoto desconhecido - " + e);
        }catch(IOException e){
            System.out.println("Erro E/S - " + e);
        }catch(Exception e){
            System.out.println("Erro - " + e);
        }finally{
            if(myRemoteService != null){

                myRemoteService.setFout(null);

                try{
                    UnicastRemoteObject.unexportObject(myRemoteService, true);
                }catch(NoSuchObjectException ignored){}
            }
        }
    }

    private void StartmyRMI(){
        try{

            try{
                LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
                System.out.println("Registry lancado!");
            }catch(RemoteException e){
                //Provavelmente ja tinha sido lancado.
            }

            /*
             * Cria o proprio servico.
             */

            myRemoteService = new RemoteDatabase();

            /*
             * Regista o servico com o nome "backup" para que os clientes possam encontra'-lo, ou seja,
             * obter a sua referencia remota.
             */

            Naming.bind(NameMyRmi, myRemoteService);

        }catch(RemoteException e){
            System.out.println("Erro remoto - " + e);
            System.exit(1);
        }catch(Exception e){
            System.out.println("Erro - " + e);
            System.exit(1);
        }

    }



}