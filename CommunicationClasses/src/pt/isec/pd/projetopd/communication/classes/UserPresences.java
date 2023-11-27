package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;

public class UserPresences implements Serializable {
    private String username;
    private String presences;

    public UserPresences(String username, String presences) {
        this.username = username;
        this.presences = presences;
    }

    public String getUsername() {
        return username;
    }

    public String getPresences() {
        return presences;
    }

    @Override
    public String toString() {
        return username + " " + presences;
    }
}
