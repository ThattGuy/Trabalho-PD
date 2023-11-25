package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;

public class EventCode implements Serializable {

    private String eventName;
    private PresenceCode eventCode;
    public EventCode(String eventName, PresenceCode eventCode) {
        this.eventName = eventName;
        this.eventCode = eventCode;
    }

    public String getEventName() {
        return eventName;
    }

    public PresenceCode getEventCode() {
        return eventCode;
    }
}
