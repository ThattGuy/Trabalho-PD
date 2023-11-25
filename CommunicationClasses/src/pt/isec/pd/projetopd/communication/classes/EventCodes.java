package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;

public class EventCodes implements Serializable {

    private Event event;
    public EventCodes(Event event) {
        this.event = event;
    }
    public Event getEvent() {
        return event;
    }

}
