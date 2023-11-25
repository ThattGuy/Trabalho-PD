package pt.isec.pd.projetopd.communication.interfaces;


import java.rmi.Remote;


public interface UpdateDB extends Remote{
    byte [] getFileChunk(String fileName, long offset) throws java.rmi.RemoteException, java.io.IOException;
    void getFile(BackupServerInterface backup) throws java.io.IOException,java.rmi.RemoteException;

}
