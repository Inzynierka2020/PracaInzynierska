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

        return new ResponseEntity<>(roundDTO, HttpStatus.CREATED);
    }

    @Override
    public RoundDTO findByRoundNum(Integer roundNum) {
        Round round = roundRepository.findByRoundNum(roundNum);
        if (round == null) {
            throw new CustomNotFoundException("Round " + roundNum + " not found");
        }
        return RoundMapper.MAPPER.toRoundDTO(round);
    }

    @Override
    public List<RoundDTO> findAll() {
        List<Round> roundList = roundRepository.findAll();
        if (roundList.size() == 0) {
            throw new CustomNotFoundException("Rounds not found");
        }
        return RoundMapper.MAPPER.toRoundDTOList(roundList);
    }

    @Override
    public List<FlightDTO> findRoundFlights(Integer roundNum) {
        List<Flight> flightList = roundRepository.findRoundFlights(roundNum);
        if (flightList.size() == 0) {
            throw new CustomNotFoundException("Flights from round " + roundNum + " not found");
        }
        return FlightMapper.MAPPER.toFlightDTOList(flightList);
    }


    @Override
    public ResponseEntity<RoundDTO> createRound(Integer roundNum, Integer eventId) {
        Round round = new Round();
        round.setRoundNum(roundNum);
        round.setEventId(eventId);
        round.setCancelled(false);
        roundRepository.save(round);
        return new ResponseEntity<>(RoundMapper.MAPPER.toRoundDTO(round), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CustomResponse> updateLocalScore(Integer roundNum) {
        Round round = roundRepository.findByRoundNum(roundNum);
        if (round.getFlights() == null) {
            throw new CustomNotFoundException("Round " + roundNum + " has no flights!");
        }
        List<Flight> validFlights = round.getFlights().stream().filter(flight -> flight.getSeconds() != null && flight.getSeconds() > 0).collect(Collectors.toList());
        if (validFlights.size() != 0) {
            Float best = validFlights.stream().min(Comparator.comparingDouble(Flight::getSeconds)).get().getSeconds();
            round.getFlights().stream().filter(flight -> flight.getSeconds() != null && flight.getSeconds() > 0).forEach(flight -> flight.setScore(best / flight.getSeconds() * 1000));
            roundRepository.save(round);
        } else {
            cancelRound(roundNum);
            throw new InvalidRoundDataException("Round " + roundNum + " cancelled because of invalid data");
        }
        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(),
                "Scores in round " + roundNum + " updated."), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponse> updateAllRounds() {
        List<Integer> roundNumbers = getRoundNumbers();
        List<Integer> cancelled = new ArrayList<>();
        for (Integer number : roundNumbers) {
            try {
                updateLocalScore(number);
            } catch (Exception ex) {
                cancelled.add(number);
            }
        }
        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(),
                roundNumbers.size() - cancelled.size() +
                        " rounds score updated, " + cancelled.size() +
                        " rounds cancelled because of invalid data"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponse> cancelRound(Integer roundNum) {
        Round round = roundRepository.findByRoundNum(roundNum);
        round.setCancelled(true);
        roundRepository.save(round);
        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(),
                "Round " + roundNum + " cancelled."), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponse> finishRound(Integer roundNum) {
        Round round = roundRepository.findByRoundNum(roundNum);
        round.setFinished(true);
        roundRepository.save(round);
        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(),
                "Round " + roundNum + " finished."), HttpStatus.OK);
    }

    @Override
    public List<Integer> getRoundNumbers() {
        return roundRepository.getRoundNumbers();
    }
}
