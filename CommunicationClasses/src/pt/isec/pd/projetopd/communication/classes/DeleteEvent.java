package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;

public class DeleteEvent implements Serializable {

    String eventName;

    public DeleteEvent(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
