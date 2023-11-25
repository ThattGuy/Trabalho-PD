package pt.isec.projetopd.serverbackup;

import pt.isec.pd.projetopd.communication.interfaces.BackupServerInterface;

public interface ServerInterface extends java.rmi.Remote {
    byte [] getFileChunk(String fileName, long offset) throws java.rmi.RemoteException, java.io.IOException;
    void getFile(String fileName, BackupServerInterface cliRef) throws java.io.IOException,java.rmi.RemoteException;
}
