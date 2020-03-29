package aviationModelling.service;

import aviationModelling.entity.Flight;
import aviationModelling.entity.Pilot;
import aviationModelling.entity.Round;
import aviationModelling.repository.RoundRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public void save(Round round) {
        roundRepository.save(round);
    }

    @Override
    public Round findByRoundNum(Integer roundNum) {
        return roundRepository.findByRoundNum(roundNum);
    }

    @Override
    public List<Round> findAll() {
        return roundRepository.findAll();
    }

    @Override
    public List<Flight> findRoundFlights(Integer roundNum) {
        return roundRepository.findRoundFlights(roundNum);
    }

    @Override
    public List<Flight> findUncancelledRoundFlights(Integer roundNum) {
        return roundRepository.findUncancelledRoundFlights(roundNum);
    }

    @Override
    public ResponseEntity<String> createRound(Integer roundNum, Integer eventId) {
        Round round = new Round();
        round.setRoundNum(roundNum);
        round.setEventId(eventId);
        round.setCancelled(false);
        save(round);
        return new ResponseEntity<>("Round " + roundNum + " created!", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> updateLocalScore(Integer roundNum) {
        Round round = findByRoundNum(roundNum);
        if(round.getFlights()==null) {
            return new ResponseEntity<>("Round "+roundNum+" has no flights!", HttpStatus.BAD_REQUEST);
        }
        List<Flight> validFlights = round.getFlights().stream().filter(flight -> flight.getSeconds() != null && flight.getSeconds() > 0).collect(Collectors.toList());
        if (validFlights.size() != 0) {
            Float best = validFlights.stream().min(Comparator.comparingDouble(Flight::getSeconds)).get().getSeconds();
            round.getFlights().stream().filter(flight -> flight.getSeconds() != null && flight.getSeconds() > 0).forEach(flight -> flight.setScore(best / flight.getSeconds() * 1000));
            save(round);
        } else {
            cancelRound(roundNum);
            return new ResponseEntity<>("The round "+roundNum+" was cancelled because of invalid data!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("All scores in round " + roundNum + " updated!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateAllRounds() {
        List<Integer> roundNumbers = getRoundNumbers();
        for(Integer number:roundNumbers) {
            updateLocalScore(number);
        }
        return new ResponseEntity<>("All local scores was updated!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> cancelRound(Integer roundNum) {
        Round round = findByRoundNum(roundNum);
        round.setCancelled(true);
        save(round);
        return new ResponseEntity<>("Round " + roundNum + " cancelled!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> finishRound(Integer roundNum) {
        Round round = findByRoundNum(roundNum);
        round.setFinished(true);
        save(round);
        return new ResponseEntity<>("Round " + roundNum + " is finished now!", HttpStatus.OK);
    }

    @Override
    public List<Integer> getRoundNumbers() {
        return roundRepository.getRoundNumbers();
    }
}
