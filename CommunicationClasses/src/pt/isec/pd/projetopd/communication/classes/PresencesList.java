package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;

public class PresencesList implements Serializable {

    String presences;

    public PresencesList(String presences) {
        this.presences = presences;
    }

    @Override
    public String toString() {
        return presences;
    }
}
