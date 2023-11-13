package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.communication.classes.ServerPort;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HandleClient implements Runnable {

    private Socket socket;
    private ServerInfo serverInfo;

    public HandleClient(Socket sock, ServerInfo serverInfo) {
        this.socket = sock;
        this.serverInfo = serverInfo;
    }

    @Override
    public void run() {
        System.out.println("I started on handle");
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            sendPort(new ServerPort(7001), out);
            do{
                Object o = in.readObject();
                System.out.println("I Have received the info");
                o = serverInfo.updateDB(o);
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
            System.out.println("I wrote");

            out.flush();
            out.reset();
        } catch (IOException e) {
            System.out.println("<" + Thread.currentThread().getName() + ">:\n\t" + e);
        }
    }


}