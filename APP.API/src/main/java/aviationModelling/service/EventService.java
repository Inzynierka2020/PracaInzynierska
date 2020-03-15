package aviationModelling.service;

import aviationModelling.entity.Event;

public interface EventService {
    Event findById(int id);
    void save(Event event);
    boolean saveEventAndPilots(int eventId);
}
