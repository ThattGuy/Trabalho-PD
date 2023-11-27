package pt.isec.pd.projetopd.communication.interfaces;

import java.rmi.Remote;

public interface BackupServerInterface extends Remote {
        void writeFileChunk(byte [] fileChunk, int nbytes) throws java.io.IOException,java.rmi.RemoteException;
        void updateDB(Object data) throws java.io.IOException,java.rmi.RemoteException;

}
