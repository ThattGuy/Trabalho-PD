package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;

public class PresencesList implements Serializable {

    String username;
    String presences;

    public PresencesList(String presences, String username) {
        this.presences = presences;
    }

    @Override
    public String toString() {
        return presences;
    }

    public String getUsername() {
        return username;
    }
}
