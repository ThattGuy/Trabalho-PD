package pt.isec.pd.projetopd.communication.classes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EventList implements Serializable {
    List<Event> events;

    public EventList(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }
}
