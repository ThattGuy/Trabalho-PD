package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Event implements Serializable {

    private String name;
    private String location;
    private String date;
    private String beginning;
    private String endTime;
    private List<RegisterCode> registerCodes;
    public Event(String name, String location, String date, String beginning, String endTime, List<RegisterCode> registerCodes) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.beginning = beginning;
        this.endTime = endTime;
        this.registerCodes = registerCodes;
    }

    public String getName() {return name;}
    public String getLocation() {return location;}
    public String getDate() {return date;}
    public String getBeginning() {return beginning;}
    public String getEndTime() {return endTime;}

    public List<RegisterCode> getPresenceCodes() {
        return registerCodes;
    }

    public List<String> getVariables() {
        return List.of(name, location, date, beginning, endTime);
    }

    public void addPresenceCode(RegisterCode newRegisterCode) {
        if (this.registerCodes == null) {
            this.registerCodes = new ArrayList<>();
        }
        this.registerCodes.add(newRegisterCode);
    }

    @Override
    public String toString() {
        return String.format("Event Name: %s; Location: %s; Date: %s; Beginning %s - %s\n", name, location, date, beginning, endTime);
    }

}
