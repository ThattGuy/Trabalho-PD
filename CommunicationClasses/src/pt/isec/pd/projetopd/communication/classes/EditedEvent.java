package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;

public class EditedEvent implements Serializable {

    private Event event;
    private String oldName;

    public EditedEvent(Event event, String oldName) {
        this.event = event;
        this.oldName = oldName;
    }

    public Event getEvent() {
        return event;
    }

    public String getOldName() {
        return oldName;
    }
}
