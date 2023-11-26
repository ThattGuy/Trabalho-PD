package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;

public class RemovePresence implements Serializable {

    private final String eventName;
    private final String user;

    public RemovePresence(String user,String eventName) {
        this.user = user;
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public String getUser() {
        return user;
    }
}
