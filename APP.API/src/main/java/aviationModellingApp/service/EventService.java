package aviationModellingApp.service;

import aviationModellingApp.entity.Event;

public interface EventService {
    Event findById(int id);
    void save(Event event);
    boolean saveEventAndPilots(int eventId);
}
