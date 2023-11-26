package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;

public class EventPresenceRequest implements Serializable {
    private Event event;
    public EventPresenceRequest(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }
}

