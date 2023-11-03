package pt.isec.pa.projetopd.cliente.model.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientData {
    public static String IP_ADDRESS_OF_SERVER;
    public static int CONNECTION_PORT; //Porto TCP
    private final Socket socket = null;


    public ClientData(String ip, int port) {
        CONNECTION_PORT = port;
        IP_ADDRESS_OF_SERVER = ip;
    }
}
