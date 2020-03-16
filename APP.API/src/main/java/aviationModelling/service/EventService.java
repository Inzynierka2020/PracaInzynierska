package aviationModelling.service;

import aviationModelling.entity.Event;

public interface EventService {
    Event findById(int id);
    boolean save(Event event);
    boolean saveEventAndPilots(int eventId);
}
