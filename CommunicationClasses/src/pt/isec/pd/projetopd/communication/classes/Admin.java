package pt.isec.pd.projetopd.communication.classes;

public class Admin extends User{
    boolean isAdmin = true;

    public Admin(String username, String password, String name, int studentNumber, int nif, String id, String address) {
        super(username, password, name, studentNumber, nif, id, address);
    }
}
