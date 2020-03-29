package aviationModelling.service;

import aviationModelling.dto.VaultEventDataDTO;
import aviationModelling.entity.Event;
import aviationModelling.entity.Flight;
import aviationModelling.entity.Pilot;
import aviationModelling.mapper.EventMapper;
import aviationModelling.mapper.FlightMapper;
import aviationModelling.mapper.PilotMapper;
import aviationModelling.repository.EventRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        Event event = null;

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
    public ResponseEntity<String> initializeDbWithDataFromVault(int eventId) {

        VaultEventDataDTO eventData = vaultService.retrieveEventData(eventId);
        saveEventToDb(eventId, eventData);
        createRoundsInDb(eventData);
        savePilotsToDb(eventData);
        saveFlightsToDb(eventData);
//        jak tutaj wrzuce update, to nie dziala


        return new ResponseEntity<>("All event data saved successfully", HttpStatus.CREATED);
    }

    private void createRoundsInDb(VaultEventDataDTO eventData) {
        List<Integer> roundNumbers = new ArrayList<>();
        eventData.getEvent().getPilots().get(0).getFlights().forEach(flight -> roundNumbers.add(flight.getRound_number()));

        for (Integer number:roundNumbers) {
            roundService.createRound(number);
            roundService.finishRound(number);
        }
    }

    private void saveFlightsToDb(VaultEventDataDTO eventData) {
        List<List<Flight>> flightList = new ArrayList<>();
//        dodaj do listy list listę przelotow kazdego pilota + zapisz
        eventData.getEvent().getPilots().forEach(pilot -> {
            if (pilot.getFlights() != null) {
                flightList.add(FlightMapper.MAPPER.toFlightList(pilot.getFlights()));
            }
        });
        flightList.forEach(list->flightService.saveAll(list));
    }

    private void savePilotsToDb(VaultEventDataDTO eventData) {
//        zapisz pilotow do bazy
        List<Pilot> pilotList = PilotMapper.MAPPER.toPilotList(eventData.getEvent().getPilots());
        pilotService.saveAll(pilotList);
    }

//    private void createRoundsInDb(Event event) {


    private void saveEventToDb(int eventId, VaultEventDataDTO eventData) {
//        zapisywanie eventu
        Event event = EventMapper.MAPPER.toEvent(eventData.getEvent());
        event.setEventId(eventId);
        save(event);
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
                List<Flight> first4 = pilotFlights.subList(0, 4).stream().filter(flight -> flight.getSeconds() != null && flight.getSeconds() > 0).collect(Collectors.toList());
                if (first4.size() != 0) {
                    Flight worst = first4.stream().min(Comparator.comparingDouble(Flight::getScore)).get();
                    pilot.setDiscarded1(worst.getScore());
                    pilotFlights.remove(worst);
                } else {
                    continue;
                }
            }

            if (totalRounds >= 15) {
                List<Flight> first15 = pilotFlights.subList(0, 14).stream().filter(flight -> flight.getSeconds() != null && flight.getSeconds() > 0).collect(Collectors.toList());
                if (first15.size() != 0) {
                    Flight worst = first15.stream().min(Comparator.comparingDouble(Flight::getScore)).get();
                    pilot.setDiscarded2(worst.getScore());
                    pilotFlights.remove(worst);
                } else {
                    continue;
                }
            }

            Float totalScore = (float) pilotFlights.stream().filter(flight -> flight.getScore() != null && flight.getScore() > 0).mapToDouble(flight -> flight.getScore()).sum();
            if (totalScore != null && totalScore > 0) pilot.setScore(totalScore);
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
