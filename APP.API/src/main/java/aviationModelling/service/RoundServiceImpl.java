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
    public ResponseEntity<String> createRound(Integer roundNum) {
        Round round = new Round();
        round.setRoundNum(roundNum);
        round.setCancelled(false);
        save(round);
        return new ResponseEntity<>("Round " + roundNum + " created!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateScore(Integer roundNum) {
        Round round = findByRoundNum(roundNum);
        if (round.getFlights().size() == 1) {
            return new ResponseEntity<>("Flight saved!", HttpStatus.OK);
        }
        Float best = round.getFlights().stream().min(Comparator.comparingDouble(Flight::getSeconds)).get().getSeconds();
        round.getFlights().forEach(flight -> flight.setScore(best / flight.getSeconds() * 1000));
        save(round);
        return new ResponseEntity<>("Flight saved, all scores recalculated", HttpStatus.OK);
    }

    @Override
    public Integer countRounds() {
        return roundRepository.countRoundsByIsCancelledFalse();
    }

    @Override
    public ResponseEntity<String> cancelRound(Integer roundNum) {
        Round round = findByRoundNum(roundNum);
        round.setCancelled(true);
        save(round);
        return new ResponseEntity<>("Round " + roundNum + " cancelled!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateGeneralScore(Integer roundNum) {
        if (!countRounds().equals(4) && !countRounds().equals(15)) {
            return classicUpdate(roundNum);
        } else {
            return specialUpdate(roundNum);
        }
    }

    private ResponseEntity<String> classicUpdate(Integer roundNum) {
        Round round = findByRoundNum(roundNum);
        List<Pilot> pilotList = pilotService.findAll();
        Map<Integer, Float> results = new HashMap<>();
        round.getFlights().stream()
                .filter(flight -> flight.isDiscarded() == false)
                .forEach(flight -> results.put(flight.getFlightId().getPilotId(), flight.getScore() - flight.getPenalty()));

        pilotList.stream()
                .filter(pilot -> results.containsKey(pilot.getId()))
                .forEach(pilot -> pilot.setScore(pilot.getScore() +
                        results.get(pilot.getId())));


        pilotService.saveAll(pilotList);
        return new ResponseEntity<>("General score updated", HttpStatus.OK);
    }

    private ResponseEntity<String> specialUpdate(Integer roundNum) {
        List<Pilot> pilotList = pilotService.findAll();

        for(Pilot tmpPilot : pilotList) {
            Pilot pilot = pilotService.findById(tmpPilot.getId());
            if(pilot.getFlights().size()==0) continue;

            pilot.getFlights().stream()
                    .filter(flight -> !flight.isDiscarded() && !flight.getRound().isCancelled())
                    .min(Comparator.comparingDouble(Flight::getScore))
                    .get()
                    .setDiscarded(true);

            Float totalScore = 0F;
            for(Flight flight:pilot.getFlights()) {
                if(flight.isDiscarded()==false && flight.getRound().isCancelled()==false) {
                    totalScore+=flight.getScore();
                }
            }
            pilot.setScore(totalScore);
        }
        pilotService.saveAll(pilotList);


        return new ResponseEntity<>("General score updated with the worst flight discard", HttpStatus.OK);
    }


}
