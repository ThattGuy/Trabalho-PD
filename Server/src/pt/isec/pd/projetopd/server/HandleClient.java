package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.communication.classes.RESPONSE;
import pt.isec.pd.projetopd.communication.classes.ServerPort;
import pt.isec.pd.projetopd.communication.classes.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HandleClient implements Runnable {

    private Socket socket;
    private HandleRequests handleRequests;
    //private ServerInfo serverInfo;

    public HandleClient(Socket sock, HandleRequests handle) {
        this.socket = sock;
        this.handleRequests = handle;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            sendPort(new ServerPort(7001), out);
            do{
                Object o = in.readObject();
                o = handleRequests.receive(o, out);
                //o = serverInfo.updateDB(o,out);

                out.writeObject(o);

                out.flush();
                out.reset();

            }while(true);




        }catch(IOException e){
            System.out.println("<" + Thread.currentThread().getName() + ">:\n\t" + e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (IOException e) {
            }
        }

    }


    private void sendPort(Object o, ObjectOutputStream out) {

        //Criar objeto para enviar o Port
        try {
            out.writeObject(new ServerPort(7001));

            out.flush();
            out.reset();
        } catch (IOException e) {
            System.out.println("<" + Thread.currentThread().getName() + ">:\n\t" + e);
        }
    }


}