package pt.isec.pd.projetopd.cliente.communication;

import pt.isec.pd.projetopd.cliente.model.data.ClientData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCPConnectionSend {

    public class TCPconnectionSend extends Thread {

        public static String IP_ADDRESS_OF_SERVER;
        public static int CONNECTION_PORT; //Porto TCP
        private final Socket socket = null;


        public TCPconnectionSend(String ip, int port) {
            CONNECTION_PORT = port;
            IP_ADDRESS_OF_SERVER = ip;
            socket = new Socket(InetAddress.getByName(ClientData.IP_ADDRESS_OF_SERVER), ClientData.CONNECTION_PORT);
        }

        public void send(String message)
        {

            try {
                // Send data to the server
                OutputStream out = socket.getOutputStream();


                String filename = "test";
                out.write(filename.getBytes());

                // Receive and print the response from the server
                String response = in.readLine();
                System.out.println("Server response: " + response);


                // Close the socket when done
                socket.close();
            } catch (IOException e) {
                System.err.println ("Error connecting to Server" + e);
            }
        }
    }

}
