package pt.isec.projetopd.serverbackup;

public class Main {

    public static void main(String[] args) {

        System.out.println("Hello world!");
        if(args.length != 1)
            return;

        Manager manager = new Manager(args[0]);
    }
}