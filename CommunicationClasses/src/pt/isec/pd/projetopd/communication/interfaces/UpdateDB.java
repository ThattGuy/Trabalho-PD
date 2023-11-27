package pt.isec.pd.projetopd.communication.interfaces;


import java.rmi.Remote;


public interface UpdateDB extends Remote{
    void getFile(BackupServerInterface backup) throws java.io.IOException,java.rmi.RemoteException;
    void deleteBackup(BackupServerInterface backup) throws java.io.IOException,java.rmi.RemoteException;
}
