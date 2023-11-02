package pt.isec.pa.projetopd.cliente;

import pt.isec.pa.projetopd.cliente.model.Manager;

public class Main {

    public static Manager manager;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Syntax: <IP_ADDRESS> <PortUDP>");
            return;
        }
        manager = new Manager(args[0], Integer.parseInt(args[1]));
    }
}