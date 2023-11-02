package pt.isec.pa.projetopd.cliente.model.data;

import java.net.Socket;
import java.util.Scanner;

public class ClientData {
    private static int CONNECTION_PORT; //Porto TCP
    private static String IP_ADDRESS_OF_SERVER;
    private final Socket socket = null;


    public static String getIpAddressOfServer() {
        return IP_ADDRESS_OF_SERVER;
    }

    public static int getConnectionPort() {
        return CONNECTION_PORT;
    }
}
