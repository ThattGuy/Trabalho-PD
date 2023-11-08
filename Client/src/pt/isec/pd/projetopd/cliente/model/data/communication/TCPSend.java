package pt.isec.pd.projetopd.cliente.model.data.communication;

import pt.isec.pd.projetopd.cliente.model.data.Data;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.Socket;

public class TCPSend {
    private Data data;
    private Socket socket;

    private ObjectOutputStream out = null;

    public TCPSend(Data data) {
        this.data = data;
        socket = data.getSocket();

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (ConnectException e) {
            String errorMessage = "Error connecting to Server, TCPSend: " + e.getMessage();
            data.setMessage(errorMessage);
        } catch (IOException e) {
            String errorMessage = "Error connecting to Server, TCPSend: " + e.getMessage();
            data.setMessage(errorMessage);
        }
    }

    public void sendObject(Serializable objectToSend) {
        try {
            out.writeObject(objectToSend);
            out.flush();
        } catch (IOException e) {
            data.setMessage("Error sending object to Server: " + e);
        }
    }
}


