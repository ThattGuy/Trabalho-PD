package pt.isec.pd.projetopd.server;


import pt.isec.pd.projetopd.communication.classes.Authentication;
import pt.isec.pd.projetopd.communication.classes.ServerPort;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

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

        boolean close = true;

        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            sendPort(new ServerPort(7001), out);
            socket.setSoTimeout(10000);
            do{
                Object o = in.readObject();
                System.out.println("<" + Thread.currentThread().getName() + ">:\n\t" + o);
                if(o == null) { socket.close(); return;}

                if(o instanceof Authentication)  socket.setSoTimeout(0);
                o = handleRequests.receive(o, out);

                out.writeObject(o);
                out.flush();
                out.reset();
            }while(true);


        }catch(IOException | ClassNotFoundException e){
            try {
                if(close) {
                    socket.close();
                    System.out.println("I closed the socket");
                    throw new RuntimeException(e);
                }

                //TODO: Tenho de retornar esta excessao e de alguma forma o servidor tem de saber que esta thread foi a baixo
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