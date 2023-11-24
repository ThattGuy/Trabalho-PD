package pt.isec.pd.projetopd.communication.classes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EventList implements Serializable {
    ArrayList<Event> events;

    public EventList(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}
