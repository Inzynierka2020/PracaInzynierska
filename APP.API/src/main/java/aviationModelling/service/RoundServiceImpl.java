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
import aviationModelling.repository.EventRepository;
import aviationModelling.repository.EventRoundRepository;
import aviationModelling.repository.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private EventRepository eventRepository;

//    public RoundServiceImpl(RoundRepository roundRepository, EventRoundRepository eventRoundRepository, VaultService vaultService) {
//        this.roundRepository = roundRepository;
//        this.eventRoundRepository = eventRoundRepository;
//        this.vaultService = vaultService;
//    }


    public RoundServiceImpl(RoundRepository roundRepository, EventRoundRepository eventRoundRepository, VaultService vaultService, EventRepository eventRepository) {
        this.roundRepository = roundRepository;
        this.eventRoundRepository = eventRoundRepository;
        this.vaultService = vaultService;
        this.eventRepository = eventRepository;
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
            eventRoundRepository.save(eventRound);
        }
        return new ResponseEntity<>(RoundMapper.MAPPER.toRoundDTO(eventRound), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<RoundDTO> createRound(RoundDTO roundDTO) {
        if (roundRepository.findByRoundNum(roundDTO.getRoundNum()) == null) {
            Round round = new Round();
            round.setRoundNum(roundDTO.getRoundNum());
            roundRepository.save(round);
        }
        if (roundDTO.getNumberOfGroups() == null) roundDTO.setNumberOfGroups(1);
        eventRoundRepository.save(RoundMapper.MAPPER.toEventRound(roundDTO));
        return new ResponseEntity<>(roundDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CustomResponse> cancelRound(Integer roundNum, Integer eventId) {
        EventRound eventRound = roundRepository.findEventRound(roundNum, eventId);
        eventRound.setCancelled(true);
        eventRoundRepository.save(eventRound);
        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(),
                "Round " + roundNum + " cancelled."), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponse> uncancelRound(Integer roundNum, Integer eventId) {
        EventRound eventRound = roundRepository.findEventRound(roundNum, eventId);
        eventRound.setCancelled(false);
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
    public RoundDTO findEventRound(Integer roundNum, Integer eventId) {
        EventRound eventRound = roundRepository.findEventRound(roundNum, eventId);
        if (eventRound == null) {
            throw new CustomNotFoundException("Round " + roundNum + " not found");
        }
        return RoundMapper.MAPPER.toRoundDTO(eventRound);
    }
//

    //
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
        return FlightMapper.MAPPER.toFlightDTO(roundRepository.findBestRoundFlight(roundNum, eventId));
    }

    @Override
    public ResponseEntity<VaultResponseDTO> updateEventRoundStatus(Integer roundNum, Integer eventId) {
        VaultResponseDTO response = vaultService.updateEventRoundStatus(roundNum, eventId);
        if (response.getResponse_code().equals(0)) {
            throw new RuntimeException(response.getError_string());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
