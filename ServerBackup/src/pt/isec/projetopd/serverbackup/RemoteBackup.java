package pt.isec.projetopd.serverbackup;

public interface RemoteBackup {

    void WriteDatabase(byte [] fileChunk, int nbytes) throws java.io.IOException;
}
