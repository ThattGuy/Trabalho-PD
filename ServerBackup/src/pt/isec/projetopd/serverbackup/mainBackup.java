package pt.isec.projetopd.serverbackup;

public class mainBackup {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java BackupDatabase <destination_path>");
            return;
        }

        Manager man = new Manager(args[0]);
        man.start();

    }
}