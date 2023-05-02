package Services;

import java.sql.SQLException;
import java.util.*;
import Services.EventListener;

public class EventManager {

    Map<String, List<EventListener>> listeners = new HashMap<>();

    public EventManager(String... operations) {
        for (String operation : operations) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }

    public void subscribe(String eventtype, EventListener listener) {
        List<EventListener> users = listeners.get(eventtype);
        users.add(listener);
    }

    public void unsubscribe(String eventtype, EventListener listener) {
        List<EventListener> users = listeners.get(eventtype);
        users.remove(listener);
    }

    public void notify(String eventtype, String energyData) throws SQLException {
        List<EventListener> users = listeners.get(eventtype);
        for (EventListener listener : users) {
            listener.update(eventtype, energyData);
        }
    }


}



