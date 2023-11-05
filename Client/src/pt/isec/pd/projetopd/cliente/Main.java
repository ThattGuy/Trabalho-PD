package pt.isec.pd.projetopd.cliente;

import javafx.application.Application;
import pt.isec.pd.projetopd.cliente.model.Manager;
import pt.isec.pd.projetopd.cliente.ui.MainJFX;

public class Main {

    public static Manager manager;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Syntax: <IP_ADDRESS> <PortUDP>");
            return;
        }

        manager = new Manager(args[0], Integer.parseInt(args[1]));

        Application.launch(MainJFX.class,args);
    }
}