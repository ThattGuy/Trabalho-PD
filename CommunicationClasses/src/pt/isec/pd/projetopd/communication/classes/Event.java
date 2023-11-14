package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;
import java.util.List;

public class Event implements Serializable {

    private String name;
    private String location;
    private String date;
    private String beginning;
    private String endTime;
    private List<User> users; //todo string ou user?

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
    public List<User> getUsers() {return users;}

    @Override
    public String toString() {
        return String.format("\"%s\";\"%s\";\"%s\";\"%s - %s\"", name, location, date, beginning, endTime);
    }
}
