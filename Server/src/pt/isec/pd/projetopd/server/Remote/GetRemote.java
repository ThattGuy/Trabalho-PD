package pt.isec.pd.projetopd.server.Remote;

import pt.isec.pd.projetopd.server.data.DataBase.DataBase;
//import pt.isec.projetopd.serverbackup.BackupServerInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;


public class GetRemote extends UnicastRemoteObject implements UpdateDB{

    public static final int MAX_CHUNCK_SIZE = 10000; //bytes
    File directory;
    protected GetRemote(File direc) throws RemoteException {
        this.directory = direc;
    }

    //protected FileInputStream getRequestedFileInputStream(String fileName) throws IOException

    @Override
    public byte[] getFileChunk(String fileName, long offset) throws RemoteException, IOException {
        return new byte[0];
    }
       /* String requestedCanonicalFilePath;

        fileName = fileName.trim();


         * Verifica se o ficheiro solicitado existe e encontra-se por baixo da localDirectory.
         *

        requestedCanonicalFilePath = new File(directory + File.separator + fileName).getCanonicalPath();

        if (!requestedCanonicalFilePath.startsWith(directory.getCanonicalPath() + File.separator)) {
            System.out.println("Nao e' permitido aceder ao ficheiro " + requestedCanonicalFilePath + "!");
            System.out.println("A directoria de base nao corresponde a " + directory.getCanonicalPath() + "!");
            throw new AccessDeniedException(fileName);
        }

        return new FileInputStream(requestedCanonicalFilePath);
    }


    @Override
    public byte [] getFileChunk(String fileName, long offset) throws RemoteException, IOException {

        byte [] fileChunk = new byte[MAX_CHUNCK_SIZE];
        int nbytes;

        fileName = fileName.trim();
        //System.out.println("Recebido pedido para: " + fileName);

        try(FileInputStream requestedFileInputStream = getRequestedFileInputStream(fileName)){

            /*
             * Obtem um bloco de bytes do ficheiro, omitindo os primeiros offset bytes.
             *
            requestedFileInputStream.skip(offset);
            nbytes = requestedFileInputStream.read(fileChunk);

            if(nbytes == -1){//EOF
                return null;
            }

            /*
             * Se fileChunk nao esta' totalmente preenchido (MAX_CHUNCK_SIZE), recorre-se
             * a um array auxiliar com tamanho correspondente ao numero de bytes efectivamente lidos.
             *
            if(nbytes < fileChunk.length){
                return Arrays.copyOf(fileChunk, nbytes);
            }

            return fileChunk;

        }catch(IOException e){
            System.out.println("Ocorreu a excecao de E/S: \n\t" + e);
            throw new IOException(fileName, e.getCause());
        }

    }

    /*
    @Override
    public void getFile(String fileName, BackupServerInterface cliRemoto) throws IOException {
        byte [] fileChunk = new byte[MAX_CHUNCK_SIZE];
        int nbytes;

        fileName = fileName.trim();
        System.out.println("Recebido pedido para: " + fileName);

        try(FileInputStream requestedFileInputStream = getRequestedFileInputStream(fileName)){

            /*
             * Obtem os bytes do ficheiro por blocos de bytes ("file chunks").

            while((nbytes = requestedFileInputStream.read(fileChunk))!=-1){

                /*
                 * Escreve o bloco actual no cliente, invocando o metodo writeFileChunk da
                 * sua interface remota.



                cliRemoto.writeFileChunk(fileChunk, nbytes);

            }

            System.out.println("Ficheiro " + new File(directory+File.separator+fileName).getCanonicalPath() +
                    " transferido para o cliente com sucesso.");
            System.out.println();

            return;

        }catch(FileNotFoundException e){   //Subclasse de IOException
            System.out.println("Ocorreu a excecao {" + e + "} ao tentar abrir o ficheiro!");
            throw new FileNotFoundException(fileName);
        }catch(IOException e){
            System.out.println("Ocorreu a excecao de E/S: \n\t" + e);
            throw new IOException(fileName, e.getCause());
        }
    }*/

}
