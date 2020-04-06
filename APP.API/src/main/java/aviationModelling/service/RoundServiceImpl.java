package aviationModelling.service;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.RoundDTO;
import aviationModelling.entity.Flight;
import aviationModelling.entity.Pilot;
import aviationModelling.entity.Round;
import aviationModelling.exception.CustomNotFoundException;
import aviationModelling.exception.CustomResponse;
import aviationModelling.exception.InvalidRoundDataException;
import aviationModelling.mapper.FlightMapper;
import aviationModelling.mapper.RoundMapper;
import aviationModelling.repository.RoundRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoundServiceImpl implements RoundService {

    private RoundRepository roundRepository;
    private PilotService pilotService;

    public RoundServiceImpl(RoundRepository roundRepository, PilotService pilotService) {
        this.roundRepository = roundRepository;
        this.pilotService = pilotService;
    }

    @Override
    public ResponseEntity<RoundDTO> save(RoundDTO roundDTO) {
        roundRepository.save(RoundMapper.MAPPER.toRound(roundDTO));
        return new ResponseEntity<>(roundDTO, HttpStatus.CREATED);
    }

    @Override
    public RoundDTO findByRoundNumAndEventId(Integer roundNum, Integer eventId) {
        Round round = roundRepository.findByRoundNumAndEventId(roundNum, eventId);
        if (round == null) {
            throw new CustomNotFoundException("Round " + roundNum + " not found");
        }
        return RoundMapper.MAPPER.toRoundDTO(round);
    }

    @Override
    public List<RoundDTO> findAll(Integer eventId) {
        List<Round> roundList = roundRepository.findAll(eventId);
        if (roundList.size() == 0) {
            throw new CustomNotFoundException("Rounds not found");
        }
        return RoundMapper.MAPPER.toRoundDTOList(roundList);
    }

    @Override
    public List<FlightDTO> findRoundFlights(Integer roundNum, Integer eventId) {
        List<Flight> flightList = roundRepository.findRoundFlights(roundNum, eventId);
        if (flightList.size() == 0) {
            throw new CustomNotFoundException("Flights from round " + roundNum + " not found");
        }
        return FlightMapper.MAPPER.toFlightDTOList(flightList);
    }

    @Override
    public ResponseEntity<RoundDTO> createRound(Integer roundNum, Integer eventId) {
        Round round = new Round();
        round.setEventId(eventId);
        round.setRoundNum(roundNum);
        round.setCancelled(false);
        roundRepository.save(round);
        return new ResponseEntity<>(RoundMapper.MAPPER.toRoundDTO(round), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CustomResponse> updateLocalScore(Integer roundNum, Integer eventId) {
        Round round = roundRepository.findByRoundNumAndEventId(roundNum, eventId);
        if (round.getFlights() == null) {
            throw new CustomNotFoundException("Round " + roundNum + " has no flights!");
        }
//        zwroc liste 'punktowych' lotow (odrzuc wszystkie z czasem 0 lub null)
        List<Flight> validFlights = round.getFlights().stream().filter(flight -> flight.getSeconds() != null && flight.getSeconds() > 0).collect(Collectors.toList());

        if (validFlights.size() != 0) {
//            wyodrebnij ilosc grup, w mapowaniu domyslnie grupa="", wiec przy braku podzialu groups.size() = 1
            Set<String> groups = new HashSet<>();
            validFlights.forEach(flight -> groups.add(flight.getGroup()));

            groups.forEach(group -> {
                Float best = validFlights.stream().filter(flight -> flight.getGroup().equals(group)).min(Comparator.comparingDouble(Flight::getSeconds)).get().getSeconds();
                round.getFlights().stream().filter(flight -> flight.getSeconds() != null && flight.getSeconds() > 0 && flight.getGroup().equals(group)).forEach(flight -> flight.setScore(best / flight.getSeconds() * 1000));
                roundRepository.save(round);
            });


        } else {
            throw new InvalidRoundDataException("Round " + roundNum + " not updated");
        }
        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(),
                "Scores in round " + roundNum + " updated."), HttpStatus.OK);
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
    public ResponseEntity<CustomResponse> cancelRound(Integer roundNum, Integer eventId) {
        Round round = roundRepository.findByRoundNumAndEventId(roundNum,eventId);
        round.setCancelled(true);
        roundRepository.save(round);
        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(),
                "Round " + roundNum + " cancelled."), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponse> uncancelRound(Integer roundNum, Integer eventId) {
        Round round = roundRepository.findByRoundNumAndEventId(roundNum,eventId);
        round.setCancelled(false);
        roundRepository.save(round);
        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(),
                "Round " + roundNum + " uncancelled."), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponse> finishRound(Integer roundNum, Integer eventId) {
        Round round = roundRepository.findByRoundNumAndEventId(roundNum,eventId);
        round.setFinished(true);
        roundRepository.save(round);
        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(),
                "Round " + roundNum + " finished."), HttpStatus.OK);
    }

    @Override
    public List<Integer> getRoundNumbers(Integer eventId) {
        return roundRepository.getRoundNumbers(eventId);
    }
}
