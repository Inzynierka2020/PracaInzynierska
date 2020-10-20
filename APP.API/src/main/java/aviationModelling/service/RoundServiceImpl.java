package aviationModelling.service;

import aviationModelling.dto.EventDTO;
import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.RoundDTO;
import aviationModelling.dto.VaultResponseDTO;
import aviationModelling.entity.EventRound;
import aviationModelling.entity.Flight;
import aviationModelling.entity.Round;
import aviationModelling.exception.CustomNotFoundException;
import aviationModelling.exception.CustomResponse;
import aviationModelling.mapper.EventMapper;
import aviationModelling.mapper.FlightMapper;
import aviationModelling.mapper.RoundMapper;
import aviationModelling.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoundServiceImpl implements RoundService {

    private RoundRepository roundRepository;
    private EventRoundRepository eventRoundRepository;
    private VaultService vaultService;
    private EventRepository eventRepository;
    private PilotRepository pilotRepository;
    private FlightRepository flightRepository;

//    public RoundServiceImpl(RoundRepository roundRepository, EventRoundRepository eventRoundRepository, VaultService vaultService) {
//        this.roundRepository = roundRepository;
//        this.eventRoundRepository = eventRoundRepository;
//        this.vaultService = vaultService;
//    }


    public RoundServiceImpl(RoundRepository roundRepository, EventRoundRepository eventRoundRepository, VaultService vaultService, EventRepository eventRepository, PilotRepository pilotRepository, FlightRepository flightRepository) {
        this.roundRepository = roundRepository;
        this.eventRoundRepository = eventRoundRepository;
        this.vaultService = vaultService;
        this.eventRepository = eventRepository;
        this.pilotRepository = pilotRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    public List<RoundDTO> findAll(Integer eventId) {
        List<EventRound> roundList = roundRepository.findAll(eventId);
        if (roundList.size() == 0) {
            throw new CustomNotFoundException("Rounds not found");
        }
        return RoundMapper.MAPPER.toRoundDTOList(roundList);
    }


    @Override
    public ResponseEntity<RoundDTO> createRound(Integer roundNum, Integer eventId, Integer numberOfGroups) {
        if (roundRepository.findByRoundNum(roundNum) == null) {
            Round round = new Round();
            round.setRoundNum(roundNum);
            roundRepository.save(round);
        }
        EventRound eventRound = eventRoundRepository.findByRoundNumAndEventId(roundNum, eventId);
        if (eventRound == null) {
            eventRound = new EventRound();
            eventRound.setRoundNum(roundNum);
            eventRound.setEventId(eventId);
            eventRound.setNumberOfGroups(numberOfGroups);
            eventRound.setSynchronized(false);
            eventRoundRepository.save(eventRound);
        }
        return new ResponseEntity<>(RoundMapper.MAPPER.toRoundDTO(eventRound), HttpStatus.CREATED);
    }
//
//    @Override
//    public ResponseEntity<RoundDTO> createRound(RoundDTO roundDTO) {
//        if (roundRepository.findByRoundNum(roundDTO.getRoundNum()) == null) {
//            Round round = new Round();
//            round.setRoundNum(roundDTO.getRoundNum());
//            roundRepository.save(round);
//        }
//        if (roundDTO.getNumberOfGroups() == null) roundDTO.setNumberOfGroups(1);
//        eventRoundRepository.save(RoundMapper.MAPPER.toEventRound(roundDTO));
//        return new ResponseEntity<>(roundDTO, HttpStatus.CREATED);
//    }

    @Override
    public ResponseEntity<CustomResponse> cancelRound(Integer roundNum, Integer eventId) {
        EventRound eventRound = roundRepository.findEventRound(roundNum, eventId);
        eventRound.setCancelled(true);
        eventRound.setSynchronized(false);
        eventRoundRepository.save(eventRound);
        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(),
                "Round " + roundNum + " cancelled."), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponse> uncancelRound(Integer roundNum, Integer eventId) {
        EventRound eventRound = roundRepository.findEventRound(roundNum, eventId);
        eventRound.setCancelled(false);
        eventRound.setSynchronized(false);
        eventRoundRepository.save(eventRound);
        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(),
                "Round " + roundNum + " uncancelled."), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponse> finishRound(Integer roundNum, Integer eventId) {
        EventRound eventRound = roundRepository.findEventRound(roundNum, eventId);
        eventRound.setFinished(true);
        eventRoundRepository.save(eventRound);
        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(),
                "Round " + roundNum + " finished."), HttpStatus.OK);
    }

    //    @Override
//    public ResponseEntity<RoundDTO> save(RoundDTO roundDTO) {
//        roundRepository.save(RoundMapper.MAPPER.toRound(roundDTO));
//        return new ResponseEntity<>(roundDTO, HttpStatus.CREATED);
//    }
//
    @Override
    public EventRound findEventRound(Integer roundNum, Integer eventId) {
        EventRound eventRound = roundRepository.findEventRound(roundNum, eventId);
        if (eventRound == null) {
            throw new CustomNotFoundException("Round " + roundNum + " not found");
        }
        return eventRound;
    }

    public void synchronizeEventRound(Integer roundNum, Integer eventId) {
        final EventRound eventRound = findEventRound(roundNum, eventId);
        eventRound.setSynchronized(true);
        eventRoundRepository.save(eventRound);
    }

    public void desynchronizeEventRound(Integer roundNum, Integer eventId) {
        final EventRound eventRound = findEventRound(roundNum, eventId);
        eventRound.setSynchronized(false);
        eventRoundRepository.save(eventRound);
    }


    @Override
    public List<FlightDTO> getRoundFlights(Integer roundNum, Integer eventId) {
//        check if event exists
        final EventDTO event = EventMapper.MAPPER.toEventDTO(eventRepository.findByEventId(eventId));

        if (event == null) {
            throw new CustomNotFoundException("Event " + eventId + " doesn't exist");

        }
//        check if round exists
        final Optional<RoundDTO> foundRound = event.getRounds()
                .stream()
                .filter(round -> round.getRoundNum().equals(roundNum))
                .findFirst();

        final RoundDTO roundDTO = foundRound.orElseThrow(() -> new CustomNotFoundException("Round " + roundNum + " not found"));

        return roundDTO.getFlights();

//        List<Flight> flightList = roundRepository.findRoundFlights(roundNum, eventId);
//        if (flightList.size() == 0) {
//            throw new CustomNotFoundException("Flights from round " + roundNum + " not found");
//        }
//        return FlightMapper.MAPPER.toFlightDTOList(flightList);
    }
//


    @Override
    public ResponseEntity<CustomResponse> updateLocalScore(Integer roundNum, Integer eventId) {
        EventRound eventRound = roundRepository.findEventRound(roundNum, eventId);
        if (eventRound.getFlights() == null) {
            throw new CustomNotFoundException("Round " + roundNum + " has no flights!");
        }

        List<Flight> validFlights = getValidFlights(eventRound);

        if (validFlights.size() != 0) {
//            wyodrebnij ilosc grup, w mapowaniu domyslnie grupa="", wiec przy braku podzialu groups.size() = 1
            Set<String> groups = getGroupNames(validFlights);

            if (eventRound.getNumberOfGroups() == 1 && groups.size() != 1) {
                eventRound.setNumberOfGroups(groups.size());
            }

            groups.forEach(group -> {
                Float bestTime = findBestTime(validFlights, group);

                countFlightScore(eventRound, group, bestTime);

                eventRoundRepository.save(eventRound);
            });


        } else {
            throw new RuntimeException("Round " + roundNum + " not updated");
        }
        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(),
                "Scores in round " + roundNum + " updated."), HttpStatus.OK);
    }

    private void countFlightScore(EventRound eventRound, String group, Float bestTime) {
        eventRound.getFlights().stream().filter(flight -> flight.getSeconds() != null && flight.getGroup().equals(group))
                .forEach(flight -> {
                    if (flight.getSeconds().equals(0F)) flight.setScore(0F);
                    else flight.setScore(bestTime / flight.getSeconds() * 1000);
                });
    }

    private Float findBestTime(List<Flight> validFlights, String group) {
        final List<Flight> flights = validFlights.stream()
                .filter(flight -> flight.getGroup().equals(group) && flight.getSeconds() > 0).collect(Collectors.toList());
        if (flights.size() > 0) {
            return flights.stream()
                    .min(Comparator.comparingDouble(Flight::getSeconds))
                    .get().getSeconds();
        } else {
            return 0F;
        }
    }

    private Set<String> getGroupNames(List<Flight> validFlights) {
        Set<String> groups = new HashSet<>();
        validFlights.forEach(flight -> groups.add(flight.getGroup()));
        return groups;
    }

    //        zwroc liste 'punktowych' lotow (odrzuc wszystkie z czasem 0 lub null)
    private List<Flight> getValidFlights(EventRound eventRound) {
        return eventRound.getFlights().stream()
                .filter(flight -> flight.getSeconds() != null)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<CustomResponse> updateAllRounds(Integer eventId) {
        List<Integer> roundNumbers = getRoundNumbers(eventId);
        List<Integer> cancelled = new ArrayList<>();
        for (Integer number : roundNumbers) {
            try {
                updateLocalScore(number, eventId);
            } catch (Exception ex) {
                cancelRound(number, eventId);
                cancelled.add(number);
            }
        }
        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(),
                roundNumbers.size() - cancelled.size() +
                        " rounds score updated, " + cancelled.size() +
                        " rounds cancelled because of invalid data"), HttpStatus.OK);
    }

    @Override
    public List<Integer> getRoundNumbers(Integer eventId) {
        return roundRepository.getRoundNumbers(eventId);
    }

    @Override
    public FlightDTO findBestRoundFlight(Integer roundNum, Integer eventId) {
        final List<Flight> bestRoundFlight = roundRepository.findBestRoundFlight(roundNum, eventId);
        return FlightMapper.MAPPER.toFlightDTO(bestRoundFlight.stream().findFirst().orElse(null));
    }

    @Override
    public ResponseEntity<VaultResponseDTO> updateEventRoundStatus(Integer roundNum, Integer eventId) {
        final EventRound eventRound = findEventRound(roundNum, eventId);
        VaultResponseDTO response = vaultService.updateEventRoundStatus(roundNum, eventId, eventRound.isCancelled());
        if (response.getResponse_code().equals(0)) {
            throw new RuntimeException(response.getError_string());
        } else {
            synchronizeEventRound(roundNum, eventId);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> synchronizeAfterOffline(List<RoundDTO> dtos) {

        final List<EventRound> eventRounds = RoundMapper.MAPPER.toEventRoundList(dtos);
        createEventRoundsIfNotExist(eventRounds);
        saveFlightsToDb(dtos);
        finishFinishedRounds(eventRounds);
        cancelCancelledRounds(eventRounds);


        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(),
                "Event updated."), HttpStatus.OK);
    }

    private void cancelCancelledRounds(List<EventRound> eventRounds) {
        eventRounds.stream()
                .forEach(eventRound -> setCancelledFlag(eventRound.getRoundNum(), eventRound.getEventId(), eventRound.isCancelled()));
    }

    private void setCancelledFlag(Integer roundNum, Integer eventId, boolean isCancelled) {
        EventRound eventRound = roundRepository.findEventRound(roundNum, eventId);
        eventRound.setCancelled(isCancelled);
        eventRoundRepository.save(eventRound);
    }

    private void finishFinishedRounds(List<EventRound> eventRounds) {
        eventRounds.stream().filter(eventRound -> eventRound.isFinished())
                .forEach(eventRound -> finishRound(eventRound.getRoundNum(), eventRound.getEventId()));
    }

    private void saveFlightsToDb(List<RoundDTO> roundDTOS) {
        roundDTOS.forEach(roundDTO -> {
            roundDTO.getFlights().forEach(flightDTO -> saveFlight(flightDTO));
        });
    }

    private void saveFlight(FlightDTO flightDTO) {
        final Flight flight = createFlightEntity(flightDTO);
        flightRepository.save(flight);
    }

    private Flight createFlightEntity(FlightDTO flightDTO) {
        final Integer eventPilotId = pilotRepository.getEventPilotId(flightDTO.getPilotId(), flightDTO.getEventId());
        final Integer eventRoundId = roundRepository.getEventRoundId(flightDTO.getRoundNum(), flightDTO.getEventId());
        final Flight flight = FlightMapper.MAPPER.toFlight(flightDTO);
        flight.setFlightId(new Flight.FlightId(eventPilotId, eventRoundId));
        return flight;
    }

    private void createEventRoundsIfNotExist(List<EventRound> eventRounds) {
        eventRounds.forEach(eventRound -> createEventRoundIfNotExists(eventRound));
    }

    private void createEventRoundIfNotExists(EventRound eventRound) {
        final EventRound foundRound = roundRepository.findEventRound(eventRound.getRoundNum(), eventRound.getEventId());
        if(foundRound == null) {
            createRound(eventRound.getRoundNum(), eventRound.getEventId(), eventRound.getNumberOfGroups());
        }
    }

    @Override
    public List<RoundDTO> getDtos() {
        final EventRound eventRound1 = roundRepository.findEventRound(10, 1834);
        final EventRound eventRound2 = roundRepository.findEventRound(11, 1834);
        final List<EventRound> eventRoundsMock = new ArrayList<>(Arrays.asList(eventRound1, eventRound2));
        return RoundMapper.MAPPER.toRoundDTOList(eventRoundsMock);
    }
}
