package pt.isec.pd.projetopd.server.Remote;

import pt.isec.projetopd.serverbackup.BackupServerInterface;

import java.rmi.Remote;


public interface UpdateDB extends Remote{
    byte [] getFileChunk(String fileName, long offset) throws java.rmi.RemoteException, java.io.IOException;
    void getFile(String fileName, BackupServerInterface cliRef) throws java.io.IOException,java.rmi.RemoteException;

}
