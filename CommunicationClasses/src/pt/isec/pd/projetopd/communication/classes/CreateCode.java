package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;

public class CreateCode implements Serializable {

    private String eventName;
    private RegisterCode eventCode;
    public CreateCode(String eventName, RegisterCode eventCode) {
        this.eventName = eventName;
        this.eventCode = eventCode;
    }

    public String getEventName() {
        return eventName;
    }

    public RegisterCode getEventCode() {
        return eventCode;
    }
}
