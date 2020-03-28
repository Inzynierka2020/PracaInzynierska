package aviationModelling.service;

import aviationModelling.dto.EventDTO;
import aviationModelling.entity.Event;
import aviationModelling.entity.Flight;
import aviationModelling.entity.Pilot;
import aviationModelling.repository.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private PilotService pilotService;
    private VaultService parser;

    public EventServiceImpl(EventRepository eventRepository, PilotService pilotService, VaultService parser) {
        this.eventRepository = eventRepository;
        this.pilotService = pilotService;
        this.parser = parser;
    }

    @Override
    public Event findById(int id) {
        Optional<Event> result = eventRepository.findById(id);

        Event event=null;

        if (result.isPresent()) {
            event = result.get();
        }
        return event;
    }

    @Override
    public ResponseEntity<String> save(Event event) {
        eventRepository.save(event);
        return new ResponseEntity<>("Event saved successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> saveEventDataFromVault(int eventId) {
        Event event = parser.retrieveEventData(eventId);
        eventRepository.save(event);
        return new ResponseEntity<>("Event "+eventId+" data from Vault saved successfully", HttpStatus.CREATED);
    }
//
//    @Override
//    public ResponseEntity<String> savePilotsDataFromVault(int eventId) {
//        List<Pilot> pilotList = parser.retrievePilotList(eventId);
//        pilotService.saveAll(pilotList);
//        return new ResponseEntity<>("Pilots data from Vault saved successfully", HttpStatus.CREATED);
//    }

    @Override
    public ResponseEntity<String> updateTotalScore() {
        List<Pilot> pilotList = pilotService.findAll();
        int totalRounds;
        for (Pilot pilot : pilotList) {
            List<Flight> pilotFlights = pilotService.findUncancelledAndFinishedPilotFlights(pilot.getId());
            totalRounds = pilotFlights.size();

            if (totalRounds == 0) continue;    // aby testy się nie wywalały

            if (totalRounds >= 4) {
                Flight worst = pilotFlights.subList(0, 4).stream().min(Comparator.comparingDouble(Flight::getScore)).get();
                pilotFlights.remove(worst);
            }

            if (totalRounds >= 15) {
                Flight worst = pilotFlights.subList(0, 14).stream().min(Comparator.comparingDouble(Flight::getScore)).get();

                pilotFlights.remove(worst);
            }

            float totalScore = (float) pilotFlights.stream().mapToDouble(flight -> flight.getScore()).sum();
            pilot.setScore(totalScore);
        }
        pilotService.saveAll(pilotList);

        return new ResponseEntity<>("Total score updated successfully!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> delete(Event event) {
        eventRepository.delete(event);
        return new ResponseEntity<>("Event deleted successfully", HttpStatus.OK);
    }
}
