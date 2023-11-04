package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.server.HeartBeat.SendHBeat;

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

        try {
            System.out.println("I started");
            /*//recebe o username e password
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String receivedData = in.readLine();
            System.out.println("Received data from client: " + receivedData);

            //este codigo serve apenas para enviar uma cena para o cliente. Nos temos de aceitar e receber!
           /* OutputStream out = socket.getOutputStream();
            PrintStream pout = new PrintStream(out);
            */
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Object o = ois.readObject();
            if (o instanceof String)
                serverInfo.updateDB((String) o);


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
