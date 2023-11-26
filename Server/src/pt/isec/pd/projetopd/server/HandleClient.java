package pt.isec.pd.projetopd.server;


import pt.isec.pd.projetopd.communication.classes.Authentication;
import pt.isec.pd.projetopd.communication.classes.ServerPort;
import pt.isec.pd.projetopd.communication.classes.User;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.UUID;

public class HandleClient implements Runnable {

    private Socket socket;
    private HandleRequests handleRequests;

    private boolean LoginReceived;
    //private ServerInfo serverInfo;

    public HandleClient(Socket sock, HandleRequests handle) {
        this.socket = sock;
        this.handleRequests = handle;
        this.LoginReceived = false;

    }

    @Override
    public void run() {

        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            sendPort(new ServerPort(7001), out);
            socket.setSoTimeout(100000);
            do{
                Object o = in.readObject();
                if(o == null) { socket.close(); return;}

                o = handleRequests.receive(o, out);
                if(o instanceof User) socket.setSoTimeout(0);

                out.writeObject(o);
                out.flush();
                out.reset();
            }while(true);


        }catch(IOException | ClassNotFoundException e){
            try {
                socket.close();
                System.out.println("I closed the socket");


            } catch (IOException ex) {
                System.out.println("<" + Thread.currentThread().getName() + ">:\n\t" + ex + "Connection with client lost!");
            }
            System.out.println("<" + Thread.currentThread().getName() + ">:\n\t" + e + "Connection with client lost!");
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