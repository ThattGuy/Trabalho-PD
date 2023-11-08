package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.server.HeartBeat.SendHBeat;
import pt.isec.pd.projetopd.server.data.Authentication;
import pt.isec.pd.projetopd.server.data.RESPONSE;

import java.io.*;
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
        System.out.println("I started");
        while(true) {
            try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
                Object o = in.readObject();
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
}