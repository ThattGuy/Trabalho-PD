package pt.isec.projetopd.serverbackup.RMI;

import pt.isec.pd.projetopd.communication.interfaces.BackupServerInterface;

import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteDatabase extends UnicastRemoteObject implements BackupServerInterface {

    FileOutputStream fout;
    String dbPath;
    protected RemoteDatabase() throws RemoteException {
        super();
        //this.updateDatabase = new UpdateDatabase(path);
    }

    public void setFout(FileOutputStream localFileOutputStream) {

        fout = localFileOutputStream;
    }
    @Override
    public void writeFileChunk(byte[] fileChunk, int nbytes) throws IOException {
        System.out.println("Recebido pedido");
        if (fout == null) {
            System.out.println("Nao existe um FileOutputStream aberto para escrita!");
            throw new IOException("<CLI> Nao existe um FileOutputStream aberto para escrita!");
        }
        try {
            fout.write(fileChunk, 0, nbytes);
        } catch (IOException e) {
            System.out.println("Exceçao ao escrever no ficheiro: "+e);
            throw new IOException("<CLI> Exceçao ao escrever no ficheiro: "+e.getCause());
        }

    }

    @Override
    public void updateDB(Object data) throws IOException, RemoteException {

        //updateDatabase.update((Serializable) data);
    }


}

