package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;
import java.util.List;

public class UserPresencesList implements Serializable {

    List<Presence> presences;

    public UserPresencesList(List<Presence> presences) {
        this.presences = presences;
    }

    public List<Presence> getPresences() {
        return presences;
    }
}
