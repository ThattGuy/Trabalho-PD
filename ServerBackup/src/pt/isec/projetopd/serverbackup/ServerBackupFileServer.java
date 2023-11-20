package pt.isec.projetopd.serverbackup;

import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerBackupFileServer extends UnicastRemoteObject implements BackupServerInterface {

    FileOutputStream fout = null;

    protected ServerBackupFileServer() throws RemoteException {
    }

    public synchronized void setFout(FileOutputStream fout) {
        this.fout = fout;
    }

    @Override
    public void writeFileChunk(byte[] fileChunk, int nbytes) throws IOException {
        if (fout == null) {
            System.out.println("Nao existe um FileOutputStream aberto para escrita!");
            throw new IOException("<CLI> Nao existe um FileOutputStream aberto para escrita!");
        }
        try {
            fout.write(fileChunk, 0, nbytes);
        } catch (IOException e) {
            System.out.println("Exceçao ao escrever no ficheiro: " + e);
            throw new IOException("<CLI> Exceçao ao escrever no ficheiro: " + e.getCause());
        }
    }
}
