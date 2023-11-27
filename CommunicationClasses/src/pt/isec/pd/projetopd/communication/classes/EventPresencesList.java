package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;

public class EventPresencesList implements Serializable {

    String eventName;
    String presences;

    public EventPresencesList(String eventName,String presences) {
        this.presences = presences;
        this.eventName = eventName;
    }

    @Override
    public String toString() {
        return presences;
    }

    public String getEventName() {
        return eventName;
    }
}
