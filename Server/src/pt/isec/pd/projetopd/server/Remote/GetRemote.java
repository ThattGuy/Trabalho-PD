package pt.isec.pd.projetopd.server.Remote;

import pt.isec.pd.projetopd.communication.interfaces.UpdateDB;
import pt.isec.pd.projetopd.communication.interfaces.BackupServerInterface;
//import pt.isec.pd.projetopd.communication.interfaces.BackupServerInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GetRemote extends UnicastRemoteObject implements UpdateDB {

    public static final int MAX_CHUNCK_SIZE = 10000; //bytes
    File directory;
    private int databaseVersion;
    RemoteManager remoteManager;

    protected GetRemote(File direc, int version, RemoteManager remoteMan) throws RemoteException {
        this.directory = direc;
        this.databaseVersion = version;
        this.remoteManager = remoteMan;

    }
    protected void setDatabaseVersion(int databaseVersion){
        this.databaseVersion = databaseVersion;
    }


    @Override
    public void getFile(BackupServerInterface backup) throws IOException {

        byte[] fileBytes = Files.readAllBytes(directory.toPath());

        backup.writeFileChunk(fileBytes, fileBytes.length);


        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(databaseVersion);
        byte[] bytes = buffer.array();
        backup.writeFileChunk(bytes, bytes.length);

        if(remoteManager.backupServers.contains(backup)) remoteManager.backupServers.add(backup);
    }

    @Override
    public void deleteBackup(BackupServerInterface backup) throws IOException, RemoteException {
        remoteManager.backupServers.remove(backup);
    }
}