package aviationModelling.service;

import aviationModelling.entity.Event;
import aviationModelling.entity.Pilot;
import aviationModelling.repository.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

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
    public ResponseEntity<String> save(Event event) {
        try {
            eventRepository.save(event);
        } catch (Exception ex) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Event saved successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> saveEventAndPilotsFromVault(int eventId) {

        try {
            Event event = parser.getEventInfo();
            eventRepository.save(event);

            List<Pilot> pilotList = parser.getPilotList(eventId);
            pilotService.saveAll(pilotList);
        } catch (Exception ex) {
            new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Event and pilots saved successfully", HttpStatus.OK);
    }
}
