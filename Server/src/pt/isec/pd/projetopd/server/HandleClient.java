package pt.isec.pd.projetopd.server;

import java.io.*;
import java.net.BindException;
import java.net.Socket;

public class HandleClient implements Runnable {

    private Socket socket;
    public HandleClient(Socket sock) {
        this.socket = sock;
    }

    @Override
    public void run() {

        try{
            System.out.println("I started");
            //recebe o username e password
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String receivedData = in.readLine();
            System.out.println("Received data from client: " + receivedData);

            //este codigo serve apenas para enviar uma cena para o cliente. Nos temos de aceitar e receber!
           /* OutputStream out = socket.getOutputStream();
            PrintStream pout = new PrintStream(out);
            */
            PrintWriter writer = new PrintWriter((socket.getOutputStream()));
            writer.println("Resposta ao pedido requesitado...");
            writer.flush();

            //faz o scanner!

// CLOSE THE CONNECTION
            socket.close();


    }catch (IOException e){
        System.err.println ("I/O error - " + e);
    }

    }
}
