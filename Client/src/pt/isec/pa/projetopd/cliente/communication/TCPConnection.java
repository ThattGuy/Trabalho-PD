package pt.isec.pa.projetopd.cliente.communication;

import pt.isec.pa.projetopd.cliente.model.data.ClientData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCPConnection {

    public void TCPconnection(String message)
    {

        try {
            // Create a socket to connect to the server
            Socket socket = new Socket(InetAddress.getByName(ClientData.IP_ADDRESS_OF_SERVER), ClientData.CONNECTION_PORT);

            // Create input and output streams for the socket
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send data to the server
            OutputStream out = socket.getOutputStream();

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
