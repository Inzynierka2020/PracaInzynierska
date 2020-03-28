package aviationModelling.service;

import aviationModelling.dto.EventDTO;
import aviationModelling.dto.VaultEventDTO;
import aviationModelling.dto.VaultEventDataDTO;
import aviationModelling.entity.Event;
import aviationModelling.entity.Flight;
import aviationModelling.entity.Pilot;
import aviationModelling.mapper.EventMapper;
import aviationModelling.mapper.FlightMapper;
import aviationModelling.mapper.PilotMapper;
import aviationModelling.repository.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private PilotService pilotService;
    private FlightService flightService;
    private RoundService roundService;
    private VaultService vaultService;


    public EventServiceImpl(EventRepository eventRepository, PilotService pilotService, FlightService flightService, RoundService roundService, VaultService vaultService) {
        this.eventRepository = eventRepository;
        this.pilotService = pilotService;
        this.flightService = flightService;
        this.roundService = roundService;
        this.vaultService = vaultService;
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

//    @Override
//    public ResponseEntity<String> saveEventDataFromVault(int eventId) {
//        Event event = parser.retrieveEventData(eventId);
//        eventRepository.save(event);
//        return new ResponseEntity<>("Event "+eventId+" data from Vault saved successfully", HttpStatus.CREATED);
//    }
//
//    @Override
//    public ResponseEntity<String> savePilotsDataFromVault(int eventId) {
//        List<Pilot> pilotList = parser.retrievePilotList(eventId);
//        pilotService.saveAll(pilotList);
//        return new ResponseEntity<>("Pilots data from Vault saved successfully", HttpStatus.CREATED);
//    }


    @Override
    public ResponseEntity<String> initializeDbWithDataFromVault(int eventId) {

        VaultEventDataDTO eventData = vaultService.retrieveEventData(eventId);

        Event event = EventMapper.MAPPER.toEvent(eventData.getEvent());
        event.setEventId(eventId);
        eventRepository.save(event);

        for(int i=1; i<=event.getNumberOfRounds(); i++) {
            roundService.createRound(i);
            roundService.finishRound(i);
        }

        List<Pilot> pilotList = PilotMapper.MAPPER.toPilotList(eventData.getEvent().getPilots());
        pilotService.saveAll(pilotList);

        List<List<Flight>> flightList = new ArrayList<>();
        eventData.getEvent().getPilots().forEach(pilot -> flightList.add(FlightMapper.MAPPER.toFlightList(pilot.getFlights())));

//        flightList.forEach(list->flightService.saveAll(list));
        for(List<Flight> list:flightList) {
            flightService.saveAll(list);
        }


        return new ResponseEntity<>("All event data saved successfully", HttpStatus.CREATED);
    }

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
    public ResponseEntity<String> delete(int eventId) {
        Event event = findById(eventId);
        eventRepository.delete(event);
        return new ResponseEntity<>("Event deleted successfully", HttpStatus.OK);
    }
}
