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
        if(eventRepository.findByEventId(eventId) != null) {
            throw new RuntimeException("Event "+eventId+" already exists");
        }
        saveEventToDb(eventId, eventData);
        createRoundsInDb(eventData, eventId);
        savePilotsToDb(eventData);
        saveEventPilots(eventData, eventId);
        saveFlightsToDb(eventData, eventId);

        return new ResponseEntity<>(new CustomResponse(HttpStatus.CREATED.value(),
                "Event " + eventId + " data saved."), HttpStatus.CREATED);
    }

    private void saveEventToDb(int eventId, VaultEventDataDTO eventData) {
//        zapisywanie eventu
        Event event = VaultEventMapper.MAPPER.toEvent(eventData.getEvent());
        event.setEventId(eventId);
        eventRepository.save(event);
    }

    private void createRoundsInDb(VaultEventDataDTO eventData, Integer eventId) {
        if (eventData.getEvent().getPilots().get(0).getFlights() == null) {
            return;
        }
        List<Integer> roundNumbers = new ArrayList<>();
        eventData.getEvent().getPilots().get(0).getFlights().forEach(flight -> roundNumbers.add(flight.getRound_number()));

        int defaultNumberOfRounds = 1;
        for (Integer number : roundNumbers) {
            roundService.createRound(number, eventId, defaultNumberOfRounds);
            roundService.finishRound(number, eventId);
        }
    }

    private void savePilotsToDb(VaultEventDataDTO eventData) {
//        zapisz pilotow do bazy
        List<Pilot> pilotList = VaultPilotMapper.MAPPER.toPilotList(eventData.getEvent().getPilots());
        pilotRepository.saveAll(pilotList);
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

    @Override
    public ResponseEntity<CustomResponse> updateTotalScore(int eventId) {
        List<EventPilot> eventPilotList = pilotRepository.findAll(eventId);
        int totalRounds;
        for (EventPilot eventPilot : eventPilotList) {
            List<Flight> pilotFlights = pilotRepository
                    .findValidPilotFlights(eventPilot.getPilotId(), eventPilot.getEventId());
            totalRounds = pilotFlights.size();
            if (totalRounds == 0) continue;

            discardWorstScores(totalRounds, eventPilot, pilotFlights);

            countTotalPenalty(eventPilot, pilotFlights);

            setScore(eventPilot, pilotFlights);
        }
        normalizeToPercentages(eventPilotList);

        eventPilotRepository.saveAll(eventPilotList);

        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(), "Total score updated."),
                HttpStatus.OK);
    }



    private void normalizeToPercentages(List<EventPilot> eventPilotList) {
        Float best = eventPilotList.stream().max(Comparator.comparingDouble(EventPilot::getScore)).get().getScore();
        eventPilotList.forEach(pilot -> pilot.setPercentage(pilot.getScore() / best * 100));
    }

    private void setScore(EventPilot eventPilot, List<Flight> pilotFlights) {
        Float discarded = 0F;

        if(eventPilot.getDiscarded1()!=null) discarded+=eventPilot.getDiscarded1();
        if(eventPilot.getDiscarded2()!=null) discarded+=eventPilot.getDiscarded2();

        Float total = (float) pilotFlights.stream().mapToDouble(flight -> flight.getScore()).sum();
        eventPilot.setScore(total - eventPilot.getTotalPenalty() - discarded);
    }

    private void countTotalPenalty(EventPilot eventPilot, List<Flight> pilotFlights) {
        Integer totalPenalty = pilotFlights.stream().mapToInt(flight -> flight.getPenalty()).sum();
        eventPilot.setTotalPenalty(totalPenalty);
    }

    private void discardWorstScores(int totalRounds, EventPilot eventPilot, List<Flight> pilotFlights) {
        if (totalRounds >= 4) {
//                sortuj rosnąco po wyniku
            pilotFlights = pilotFlights.stream().sorted(Comparator.comparing(f -> f.getScore()))
                    .collect(Collectors.toList());

//                ponad 15 rozegranych kolejek - odrzuć dwa najgorsze wyniki
            if (totalRounds >= 15) {
//                sortuj odrzucone wyniki wg kolejnosci rund
                List<Flight> discarded = pilotFlights.subList(0, 2).stream()
                        .sorted(Comparator.comparing(f -> f.getFlightId().getEventRoundId()))
                        .collect(Collectors.toList());

                eventPilot.setDiscarded1(discarded.get(0).getScore());
                eventPilot.setDiscarded2(discarded.get(1).getScore());
            }
//                ponad 4 rozegrane kolejki - odrzuć najgorszy wynik
            else {
                eventPilot.setDiscarded1(pilotFlights.get(0).getScore());
            }
        }
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
