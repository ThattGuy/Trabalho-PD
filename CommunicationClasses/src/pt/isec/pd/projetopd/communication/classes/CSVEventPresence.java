package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;

public class CSVEventPresence implements Serializable {

    private final Event event;

    public CSVEventPresence(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

}
