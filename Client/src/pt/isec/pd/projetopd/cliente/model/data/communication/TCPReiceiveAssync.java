package pt.isec.pd.projetopd.cliente.model.data.communication;

import java.net.Socket;

public class TCPReiceiveAssync extends TCPReceive{

    public TCPReiceiveAssync(Socket socket, MessageReceivedListener listener) {
        super(socket, listener);
    }

    @Override
    public void run() {
        super.run();
    }


}
