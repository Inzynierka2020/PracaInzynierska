package aviationModelling.service;

import aviationModelling.dto.EventDTO;
import aviationModelling.dto.VaultEventDataDTO;
import aviationModelling.entity.*;
import aviationModelling.exception.CustomNotFoundException;
import aviationModelling.exception.CustomResponse;
import aviationModelling.mapper.*;
import aviationModelling.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private PilotRepository pilotRepository;
    private FlightRepository flightRepository;
    private EventPilotRepository eventPilotRepository;
    private RoundRepository roundRepository;
    private RoundService roundService;
    private VaultServiceImpl vaultService;


    public EventServiceImpl(EventRepository eventRepository, PilotRepository pilotRepository, FlightRepository flightRepository, EventPilotRepository eventPilotRepository, RoundRepository roundRepository, RoundService roundService, VaultServiceImpl vaultService) {
        this.eventRepository = eventRepository;
        this.pilotRepository = pilotRepository;
        this.flightRepository = flightRepository;
        this.eventPilotRepository = eventPilotRepository;
        this.roundRepository = roundRepository;
        this.roundService = roundService;
        this.vaultService = vaultService;
    }

    @Override
    public EventDTO getEvent(int id) {
        Event event = eventRepository.findByEventId(id);

        if (event == null) {
            throw new CustomNotFoundException("Event " + id + " not found.");
        }
        return EventMapper.MAPPER.toEventDTO(event);
    }


    @Override
    public ResponseEntity<CustomResponse> initializeDbWithDataFromVault(int eventId) {

        VaultEventDataDTO eventData = vaultService.getEventInfoFull(eventId);
        saveEventToDb(eventId, eventData);
        createRoundsInDb(eventData, eventId);
        savePilotsToDb(eventData);
        saveEventPilots(eventData, eventId);
        saveFlightsToDb(eventData, eventId);


        return new ResponseEntity<>(new CustomResponse(HttpStatus.CREATED.value(),
                "Event " + eventId + " data saved."), HttpStatus.CREATED);
    }

    private void saveEventPilots(VaultEventDataDTO eventData, Integer eventId) {
        eventData.getEvent().getPilots().forEach(pilot -> {
            EventPilot eventPilot = new EventPilot();
            eventPilot.setEventId(eventId);
            eventPilot.setPilotId(pilot.getPilot_id());
            eventPilot.setScore(0F);
            eventPilotRepository.save(eventPilot);
        });

    }

    private void createRoundsInDb(VaultEventDataDTO eventData, Integer eventId) {
        if (eventData.getEvent().getPilots().get(0).getFlights() == null) {
            return;
        }
        List<Integer> roundNumbers = new ArrayList<>();
        eventData.getEvent().getPilots().get(0).getFlights().forEach(flight -> roundNumbers.add(flight.getRound_number()));

        for (Integer number : roundNumbers) {
            roundService.createRound(number, eventId);
            roundService.finishRound(number, eventId);
        }
    }

    private void saveFlightsToDb(VaultEventDataDTO eventData, Integer eventId) {
        List<EventPilot> eventPilotList = pilotRepository.findAll(eventId);
        List<EventRound> eventRoundList = roundRepository.findAll(eventId);

//        dla kazdego pilota znajdz wszystkie jego loty i przypisz im uprzednio wygenerowany eventPilotId oraz eventRoundId
        eventData.getEvent().getPilots().forEach(pilot -> {

            if (pilot.getFlights() != null) {

                Integer eventPilotId = eventPilotList.stream()
                        .filter(eventPilot -> eventPilot.getPilotId().equals(pilot.getPilot_id()))
                        .findFirst().get().getEventPilotId();

                pilot.getFlights().forEach(tmpFlight -> {
                    Flight flight = VaultFlightMapper.MAPPER.toFlight(tmpFlight);
                    Integer eventRoundId = eventRoundList.stream()
                            .filter(eventRound -> eventRound.getRoundNum().equals(tmpFlight.getRound_number()))
                            .findFirst().get().getEventRoundId();

                    flight.setFlightId(new Flight.FlightId(eventPilotId, eventRoundId));
                    flightRepository.save(flight);
                });
            }
        });
    }

    private void savePilotsToDb(VaultEventDataDTO eventData) {
//        zapisz pilotow do bazy
        List<Pilot> pilotList = VaultPilotMapper.MAPPER.toPilotList(eventData.getEvent().getPilots());
        pilotRepository.saveAll(pilotList);
    }


    private void saveEventToDb(int eventId, VaultEventDataDTO eventData) {
//        zapisywanie eventu
        Event event = VaultEventMapper.MAPPER.toEvent(eventData.getEvent());
        event.setEventId(eventId);
        eventRepository.save(event);
    }


    @Override
    public ResponseEntity<CustomResponse> updateTotalScore(int eventId) {
        List<EventPilot> eventPilotList = pilotRepository.findAll(eventId);
        int totalRounds;
        for (EventPilot eventPilot : eventPilotList) {
            List<Flight> pilotFlights = pilotRepository
                    .findValidPilotFlights(eventPilot.getPilotId(), eventPilot.getEventId());
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
                eventPilot.setDiscarded1(worst.getScore());
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
                eventPilot.setDiscarded2(worst.getScore());
                pilotFlights.remove(worst);
            }

            Float totalScore = (float) pilotFlights.stream().filter(flight -> flight.getScore()!=null).mapToDouble(flight -> flight.getScore()).sum();
            if (totalScore != null) eventPilot.setScore(totalScore);
            else eventPilot.setScore(0F);
        }
        eventPilotRepository.saveAll(eventPilotList);

        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(), "Total score updated."),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponse> delete(int eventId) {
        Event event = eventRepository.findByEventId(eventId);
        if (event == null) {
            throw new CustomNotFoundException("Event " + eventId + " not found.");
        }
        eventRepository.delete(event);
        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(),
                "Event " + eventId + " deleted."), HttpStatus.OK);
    }
}
