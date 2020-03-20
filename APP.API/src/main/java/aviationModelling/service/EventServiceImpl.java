package aviationModelling.service;

import aviationModelling.entity.Event;
import aviationModelling.entity.Pilot;
import aviationModelling.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class EventServiceImpl implements EventService {

    Logger logger = Logger.getLogger(getClass().getName());

    private EventRepository eventRepository;
    private PilotService pilotService;
    private VaultParser parser;

    public EventServiceImpl(EventRepository eventRepository, PilotService pilotService, VaultParser parser) {
        this.eventRepository = eventRepository;
        this.pilotService = pilotService;
        this.parser = parser;
    }

    @Override
    public Event findById(int id) {
        Optional<Event> result = eventRepository.findById(id);

        Event event;

        if (result.isPresent()) {
            event = result.get();
        } else {
            throw new RuntimeException("Didn't find event with id = " + id);
        }
        return event;
    }

    @Override
    public boolean save(Event event) {
        try {
            eventRepository.save(event);
        } catch (Exception ex) {
            logger.warning("====> ERROR saving an event");
            return false;
        }
        return true;
    }

    @Override
    public boolean saveEventAndPilots(int eventId) {

        try {
            Event event = parser.getEventInfo();
            eventRepository.save(event);

            List<Pilot> pilotList = parser.getPilotList(eventId);
            pilotService.savePilots(pilotList);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
