package pt.isec.pa.projetopd.cliente.model.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Communication extends Thread{

    private static ClientData data;

    public void TCPconnection()
    {

        try {
            // Create a socket to connect to the server
            Socket socket = new Socket(InetAddress.getByName(ClientData.getIpAddressOfServer()), ClientData.getConnectionPort());

            // Create input and output streams for the socket
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send data to the server
            OutputStream out = socket.getOutputStream();

            String filename = username + password;
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
