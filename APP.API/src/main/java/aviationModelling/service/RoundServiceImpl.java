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
    public List<Flight> findRoundFlights(Integer roundNum) {
        return roundRepository.findRoundFlights(roundNum);
    }

    @Override
    public List<Flight> findUncancelledRoundFlights(Integer roundNum) {
        return roundRepository.findUncancelledRoundFlights(roundNum);
    }

    @Override
    public ResponseEntity<String> createRound(Integer roundNum) {
        Round round = new Round();
        round.setRoundNum(roundNum);
        round.setCancelled(false);
        save(round);
        return new ResponseEntity<>("Round " + roundNum + " created!", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> updateLocalScore(Integer roundNum) {
        Round round = findByRoundNum(roundNum);
        Float best = round.getFlights().stream().filter(flight -> flight.getSeconds()!=null).min(Comparator.comparingDouble(Flight::getSeconds)).get().getSeconds();
        round.getFlights().stream().filter(flight -> flight.getSeconds()!=null).forEach(flight -> flight.setScore(best / flight.getSeconds() * 1000));
        save(round);
        return new ResponseEntity<>("All scores in round "+roundNum+" updated!", HttpStatus.OK);
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
}
