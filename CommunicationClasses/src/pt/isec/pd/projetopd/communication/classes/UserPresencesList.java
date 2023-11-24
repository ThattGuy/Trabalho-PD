package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserPresencesList implements Serializable {

    ArrayList<Presence> presences;

    public UserPresencesList(ArrayList<Presence> presences) {
        this.presences = presences;
    }

    public List<Presence> getPresences() {
        return presences;
    }
}
