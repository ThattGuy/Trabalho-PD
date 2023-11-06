package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.server.HeartBeat.SendHBeat;
import pt.isec.pd.projetopd.server.data.Authentication;

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
        do{
            try {

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Object o = ois.readObject();

                serverInfo.updateDB((Object) o);



            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } while (true);
    }
}
