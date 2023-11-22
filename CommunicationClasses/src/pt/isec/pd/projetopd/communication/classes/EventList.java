package pt.isec.pd.projetopd.communication.classes;

import javax.xml.stream.events.EntityReference;
import java.util.List;

public class EventList {
    List<Event> events;

    public EventList(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }
}
