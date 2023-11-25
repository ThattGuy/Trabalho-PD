package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;

public class EventPresence implements Serializable {
    private Event event;
    public EventPresence(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }
}

