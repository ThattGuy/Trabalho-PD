package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.communication.classes.ServerPort;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HandleClient implements Runnable {

    private Socket socket;
    private ServerInfo serverInfo;
    private ServerSocket NotifSocket = null;

    public HandleClient(Socket sock, ServerInfo serverInfo) {
        this.socket = sock;
        this.serverInfo = serverInfo;

        try{
            this.NotifSocket = new ServerSocket(7001);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {
        System.out.println("I started");
        sendPort(new ServerPort(7001));//todo indicar porto nos argumentos do main

        while(true) {
            try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {


                Object o = in.readObject();
                System.out.println("I Have received the info");
                o = serverInfo.updateDB(o);
                out.writeObject(o);

                out.flush();
                out.reset();

            } catch (ClassNotFoundException | IOException e) {
                System.out.println("<" + Thread.currentThread().getName() + ">:\n\t" + e);
                break;
            } finally {
                try {
                    if (socket != null) socket.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private void sendPort(Object o) {

        //Criar objeto para enviar o Port
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())
        ) {
            out.writeObject(o);
            out.flush();
            out.reset();
        } catch (IOException e) {
            System.out.println("<" + Thread.currentThread().getName() + ">:\n\t" + e);
        }
    }
}