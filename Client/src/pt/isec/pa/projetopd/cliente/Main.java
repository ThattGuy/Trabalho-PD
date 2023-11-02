package pt.isec.pa.projetopd.cliente;

import pt.isec.pa.projetopd.cliente.model.Manager;

public class Main {

    public static Manager manager;

    static {
        manager = new Manager();
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}