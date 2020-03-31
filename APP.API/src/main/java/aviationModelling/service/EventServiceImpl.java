package aviationModelling.service;

import aviationModelling.dto.EventDTO;
import aviationModelling.dto.VaultEventDataDTO;
import aviationModelling.entity.Event;
import aviationModelling.entity.Flight;
import aviationModelling.entity.Pilot;
import aviationModelling.exception.CustomNotFoundException;
import aviationModelling.exception.CustomResponse;
import aviationModelling.mapper.EventMapper;
import aviationModelling.mapper.VaultEventMapper;
import aviationModelling.mapper.VaultFlightMapper;
import aviationModelling.mapper.VaultPilotMapper;
import aviationModelling.repository.EventRepository;
import aviationModelling.repository.FlightRepository;
import aviationModelling.repository.PilotRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private PilotRepository pilotRepository;
    private FlightRepository flightRepository;
    private RoundService roundService;
    private VaultServiceImpl vaultService;


    public EventServiceImpl(EventRepository eventRepository, PilotRepository pilotRepository, FlightRepository flightRepository, RoundService roundService, VaultServiceImpl vaultService) {
        this.eventRepository = eventRepository;
        this.pilotRepository = pilotRepository;
        this.flightRepository = flightRepository;
        this.roundService = roundService;
        this.vaultService = vaultService;
    }

    @Override
    public EventDTO findById(int id) {
        Optional<Event> result = eventRepository.findById(id);

        Event event = null;

        if (result.isPresent()) {
            event = result.get();
        } else {
            throw new CustomNotFoundException("Event " + id + " not found.");
        }
        return EventMapper.MAPPER.toEventDTO(event);
    }


    @Override
    public ResponseEntity<CustomResponse> initializeDbWithDataFromVault(int eventId) {

        VaultEventDataDTO eventData = vaultService.retrieveEventData(eventId);
        saveEventToDb(eventId, eventData);
        createRoundsInDb(eventData, eventId);
        savePilotsToDb(eventData);
        saveFlightsToDb(eventData);


        return new ResponseEntity<>(new CustomResponse(HttpStatus.CREATED.value(),
                "Event "+eventId+" data saved."), HttpStatus.CREATED);
    }

    private void createRoundsInDb(VaultEventDataDTO eventData, Integer eventId) {
        if (eventData.getEvent().getPilots().get(0).getFlights() == null) {
            return;
        }
        List<Integer> roundNumbers = new ArrayList<>();
        eventData.getEvent().getPilots().get(0).getFlights().forEach(flight -> roundNumbers.add(flight.getRound_number()));

        for (Integer number : roundNumbers) {
            roundService.createRound(number, eventId);
            roundService.finishRound(number);
        }
    }

    private void saveFlightsToDb(VaultEventDataDTO eventData) {
        List<List<Flight>> flightList = new ArrayList<>();
//        dodaj do listy list listę przelotow kazdego pilota + zapisz
        eventData.getEvent().getPilots().forEach(pilot -> {
            if (pilot.getFlights() != null) {
                flightList.add(VaultFlightMapper.MAPPER.toFlightList(pilot.getFlights()));
            }
        });
        flightList.forEach(list -> flightRepository.saveAll(list));
    }

    private void savePilotsToDb(VaultEventDataDTO eventData) {
//        zapisz pilotow do bazy
        List<Pilot> pilotList = VaultPilotMapper.MAPPER.toPilotList(eventData.getEvent().getPilots());
        pilotRepository.saveAll(pilotList);
    }

//    private void createRoundsInDb(Event event) {


    private void saveEventToDb(int eventId, VaultEventDataDTO eventData) {
//        zapisywanie eventu
        Event event = VaultEventMapper.MAPPER.toEvent(eventData.getEvent());
        event.setEventId(eventId);
        eventRepository.save(event);
    }

    @Override
    public ResponseEntity<CustomResponse> updateTotalScore() {
        List<Pilot> pilotList = pilotRepository.findAll();
        int totalRounds;
        for (Pilot pilot : pilotList) {
            List<Flight> pilotFlights = pilotRepository.findUncancelledAndFinishedPilotFlights(pilot.getId());
            totalRounds = pilotFlights.size();

            if (totalRounds == 0) continue;

//            po 4 rundach odrzuc najgorszy wynik
            if (totalRounds >= 4) {
                List<Flight> first4 = pilotFlights.subList(0, 4);
                first4.stream().forEach(flight -> {
                    if (flight.getScore() == null) {
                        flight.setScore(0F);
                    }
                });

                Flight worst = first4.stream().min(Comparator.comparingDouble(Flight::getScore)).get();
                pilot.setDiscarded1(worst.getScore());
                pilotFlights.remove(worst);
            }

            if (totalRounds >= 15) {
                List<Flight> first15 = pilotFlights.subList(0, 14);
                first15.stream().forEach(flight -> {
                    if (flight.getScore() == null) {
                        flight.setScore(0F);
                    }
                });
                Flight worst = first15.stream().min(Comparator.comparingDouble(Flight::getScore)).get();
                pilot.setDiscarded2(worst.getScore());
                pilotFlights.remove(worst);
            }

            Float totalScore = (float) pilotFlights.stream().filter(flight -> flight.getScore()!=null).mapToDouble(flight -> flight.getScore()).sum();
            if (totalScore != null) pilot.setScore(totalScore);
            else pilot.setScore(0F);
        }
        pilotRepository.saveAll(pilotList);

        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(), "Total score updated."),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponse> delete(int eventId) {
        Optional<Event> result = eventRepository.findById(eventId);
        Event event = null;
        if(!result.isPresent()) {
            throw new CustomNotFoundException("Event "+eventId+" not found.");
        }
        event = result.get();
        eventRepository.delete(event);
        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(),
                "Event "+eventId+" deleted."), HttpStatus.OK);
    }
}
