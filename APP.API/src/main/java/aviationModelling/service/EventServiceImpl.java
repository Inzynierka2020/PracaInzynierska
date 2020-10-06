package aviationModelling.service;

import aviationModelling.dto.EventDTO;
import aviationModelling.dto.VaultEventDataDTO;
import aviationModelling.dto.VaultFlightDTO;
import aviationModelling.dto.VaultRoundsDTO;
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
import java.util.stream.IntStream;

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
        if (isEventCreated(eventId)) {
            throw new RuntimeException("Event " + eventId + " already exists");
        }
        saveEventToDb(eventId, eventData);
        createRoundsInDb(eventData, eventId);
        savePilotsToDb(eventData);
        saveEventPilots(eventData, eventId);
        saveFlightsToDb(eventData, eventId);

        return new ResponseEntity<>(new CustomResponse(HttpStatus.CREATED.value(),
                "Event " + eventId + " data saved."), HttpStatus.CREATED);
    }

    private boolean isEventCreated(int eventId) {
        return eventRepository.findByEventId(eventId) != null;
    }

    private void saveEventToDb(int eventId, VaultEventDataDTO eventData) {
//        zapisywanie eventu
        Event event = VaultEventMapper.MAPPER.toEvent(eventData.getEvent());
        event.setEventId(eventId);
        eventRepository.save(event);
    }

    private void createRoundsInDb(VaultEventDataDTO eventData, Integer eventId) {
        if (eventData.getEvent().getTotal_rounds() == null) {
            return;
        }

        final Integer totalRounds = eventData.getEvent().getTotal_rounds();

        int defaultNumberOfGroups = 1;
        for (int i=1; i<=totalRounds; i++) {
            roundService.createRound(i, eventId, defaultNumberOfGroups);
            roundService.finishRound(i, eventId);
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
//        eventData.getEvent().getPilots().forEach(pilot -> {
        Integer totalRounds = eventData.getEvent().getTotal_rounds();

        eventData.getEvent().getPrelim_standings().getStandings().forEach(standing -> {
            Integer eventPilotId = eventPilotList.stream()
                    .filter(eventPilot -> eventPilot.getPilotId().equals(standing.getPilot_id()))
                    .findFirst().get().getEventPilotId();

            List<VaultRoundsDTO> rounds = standing.getRounds();
            rounds.stream()
                    .filter(round -> round.getRound_number()<=totalRounds)
                    .forEach(round -> {
                List<VaultFlightDTO> flights = round.getFlights();
                Integer eventRoundId = eventRoundList.stream()
                        .filter(eventRound -> eventRound.getRoundNum().equals(round.getRound_number()))
                        .findFirst().get().getEventRoundId();
                flights.forEach(tmpFlight -> {
                    Flight flight = VaultFlightMapper.MAPPER.toFlight(tmpFlight);
                    flight.setFlightId(new Flight.FlightId(eventPilotId, eventRoundId));
                    flight.setSynchronized(true);
                    flightRepository.save(flight);
                });
            });
        });
    }

    @Override
    public ResponseEntity<CustomResponse> updateAllTotalScores(int eventId) {
        List<EventPilot> eventPilotList = pilotRepository.findAll(eventId);


        for (EventPilot eventPilot : eventPilotList) {
            updatePilotTotalScore(eventPilot);
        }
        saveNormalizedScores(eventPilotList);

        eventPilotRepository.saveAll(eventPilotList);

        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(), "Total score updated."),
                HttpStatus.OK);
    }

    private void updatePilotTotalScore(EventPilot eventPilot) {
        List<Flight> pilotFlights = getValidPilotFlights(eventPilot);
        int totalRounds = pilotFlights.size();

        if (totalRounds == 0) {
            resetPilotScore(eventPilot);
            resetPilotPenalty(eventPilot);
            return;
        }

        discardWorstScores(totalRounds, eventPilot, pilotFlights);

        saveTotalPenalty(eventPilot, pilotFlights);

        setPilotScore(eventPilot, pilotFlights);
    }

    private List<Flight> getValidPilotFlights(EventPilot eventPilot) {
        return pilotRepository
                .findValidPilotFlights(eventPilot.getPilotId(), eventPilot.getEventId());
    }

    private void resetPilotScore(EventPilot eventPilot) {
        if (eventPilot.getScore() > 0) {
            eventPilot.setScore(0F);
        }
    }

    private void resetPilotPenalty(EventPilot eventPilot) {
        if (eventPilot.getTotalPenalty() > 0) {
            eventPilot.setTotalPenalty(0);
        }
    }

    private void discardWorstScores(int totalRounds, EventPilot eventPilot, List<Flight> pilotFlights) {
        final int FIRST_BORDER = 4;
        final int SECOND_BORDER = 15;

        if (totalRounds >= FIRST_BORDER) {

            List<Flight> sortedFlightList = getFlightListOrderedByScore(pilotFlights);

            if (totalRounds >= SECOND_BORDER) {
                discardTwoFlights(eventPilot, sortedFlightList);
            } else {
                discardOneFlight(eventPilot, sortedFlightList);
            }
        } else {
            resetDiscardedScores(eventPilot);
        }
    }

    private void discardOneFlight(EventPilot eventPilot, List<Flight> sortedFlightList) {
        eventPilot.setDiscarded1(sortedFlightList.get(0).getScore());
    }

    private void discardTwoFlights(EventPilot eventPilot, List<Flight> sortedFlightList) {
//        in order to print discarded value ordered by round ID
        List<Flight> discardedFlightsOrderedByRoundId = getDiscardedFlightsOrderedByRoundId(sortedFlightList);

        eventPilot.setDiscarded1(discardedFlightsOrderedByRoundId.get(0).getScore());
        eventPilot.setDiscarded2(discardedFlightsOrderedByRoundId.get(1).getScore());
    }

    private List<Flight> getDiscardedFlightsOrderedByRoundId(List<Flight> sortedFlightList) {
        return sortedFlightList.subList(0, 2).stream()
                .sorted(Comparator.comparing(f -> f.getFlightId().getEventRoundId()))
                .collect(Collectors.toList());
    }

    private void resetDiscardedScores(EventPilot eventPilot) {
        eventPilot.setDiscarded1(0F);
        eventPilot.setDiscarded2(0F);
    }

    private List<Flight> getFlightListOrderedByScore(List<Flight> pilotFlights) {
        return pilotFlights.stream().sorted(Comparator.comparing(f -> f.getScore()))
                .collect(Collectors.toList());
    }

    private void saveTotalPenalty(EventPilot eventPilot, List<Flight> pilotFlights) {
        Integer totalPenalty = countTotalPenalty(pilotFlights);
        eventPilot.setTotalPenalty(totalPenalty);
    }

    private Integer countTotalPenalty(List<Flight> pilotFlights) {
        Integer totalPenalty = pilotFlights.stream().filter(flight -> !flight.getEventRound().isCancelled()).mapToInt(flight -> flight.getPenalty()).sum();
        return totalPenalty;
    }


    private void setPilotScore(EventPilot eventPilot, List<Flight> pilotFlights) {
        Float discarded = countDiscardedPoints(eventPilot);
        Integer totalPenalty = eventPilot.getTotalPenalty();
        Float total = countTotalPoints(pilotFlights);

        eventPilot.setScore(total - totalPenalty - discarded);
    }

    private Float countDiscardedPoints(EventPilot eventPilot) {
        Float discarded = 0F;

        if (eventPilot.getDiscarded1() != null) discarded += eventPilot.getDiscarded1();
        if (eventPilot.getDiscarded2() != null) discarded += eventPilot.getDiscarded2();
        return discarded;
    }

    private float countTotalPoints(List<Flight> pilotFlights) {
        return (float) pilotFlights.stream().mapToDouble(flight -> flight.getScore()).sum();
    }

    private void saveNormalizedScores(List<EventPilot> eventPilotList) {
        long count = countPilotsWithValidScore(eventPilotList);
        if (count != 0) {
            Float best = findBestScore(eventPilotList);
            normalizePilotsScore(eventPilotList, best);
        } else {
            eventPilotList.forEach(pilot -> pilot.setPercentage(0F));
        }
    }

    private long countPilotsWithValidScore(List<EventPilot> eventPilotList) {
        return eventPilotList.stream().filter(pilot -> pilot.getScore() > 0).count();
    }

    private Float findBestScore(List<EventPilot> eventPilotList) {
        return eventPilotList.stream().max(Comparator.comparingDouble(EventPilot::getScore)).get().getScore();
    }

    private void normalizePilotsScore(List<EventPilot> eventPilotList, Float best) {
        eventPilotList.forEach(pilot -> pilot.setPercentage(pilot.getScore() / best * 100));
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
