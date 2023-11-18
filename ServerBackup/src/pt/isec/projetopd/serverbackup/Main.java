package pt.isec.projetopd.serverbackup;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        System.out.println("Hello world!");
        if(args.length != 1)
            return;

        try {
            Manager manager = new Manager(args[0]);
            manager.start();
        }catch(RuntimeException e){
            System.out.println(e);
        }

    }
}