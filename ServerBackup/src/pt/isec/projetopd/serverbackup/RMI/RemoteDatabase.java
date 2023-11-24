package pt.isec.projetopd.serverbackup.RMI;

import pt.isec.projetopd.serverbackup.BackupServerInterface;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteDatabase extends UnicastRemoteObject implements BackupServerInterface {

    protected RemoteDatabase() throws RemoteException {
        super();
    }

    @Override
    public void writeFileChunk(byte[] fileChunk, int nbytes) throws IOException {

    }
}

