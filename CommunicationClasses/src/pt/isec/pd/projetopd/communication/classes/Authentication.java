package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;

/**
 * classe para Login
 */
public class Authentication implements Serializable {

    private String username;
    private String password;

    public Authentication(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}