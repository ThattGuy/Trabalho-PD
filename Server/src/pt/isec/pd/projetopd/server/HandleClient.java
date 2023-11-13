package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.communication.classes.ServerPort;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HandleClient implements Runnable {

    private Socket socket;
    private ServerInfo serverInfo;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public HandleClient(Socket sock, ServerInfo serverInfo) {
        this.socket = sock;
        this.serverInfo = serverInfo;

        try {
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Unable to connect to socket to communicate with client!");
        }

    }

    @Override
    public void run() {
        System.out.println("I started on handle");
        sendPort(new ServerPort(7001));//todo indicar porto nos argumentos do main

        while(true) {
            System.out.println("I am waiting for info");
            try {
                System.out.println("I tryed and cant read");

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
        try {
            out.writeObject(o);
            System.out.println("I sent the port");
            out.flush();
            out.reset();
        } catch (IOException e) {
            System.out.println("<" + Thread.currentThread().getName() + ">:\n\t" + e);
        }
    }
}