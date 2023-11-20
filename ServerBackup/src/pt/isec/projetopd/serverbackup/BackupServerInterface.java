package pt.isec.projetopd.serverbackup;

public interface BackupServerInterface {
        void writeFileChunk(byte [] fileChunk, int nbytes) throws java.io.IOException;

}
