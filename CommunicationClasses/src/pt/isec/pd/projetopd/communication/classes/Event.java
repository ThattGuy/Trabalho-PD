package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;
import java.util.List;

public class Event implements Serializable {

    private String name;
    private String location;
    private String date;
    private String beginning;
    private String endTime;
    private List<RegisterCode> registerCodes;
    public Event(String name, String location, String date, String beginning, String endTime) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.beginning = beginning;
        this.endTime = endTime;
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

    public void addPresenceCode(RegisterCode registerCodes) {
        this.registerCodes.add(registerCodes);
    }

    @Override
    public String toString() {
        return String.format("Event Name: %s; Location: %s; Date: %s; Beginning %s - %s\n", name, location, date, beginning, endTime);
    }
}
