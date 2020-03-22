package aviationModelling.service;

import aviationModelling.entity.Pilot;
import aviationModelling.entity.Round;
import aviationModelling.repository.RoundRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class RoundServiceImpl implements RoundService {

    private RoundRepository roundRepository;
    private PilotService pilotService;

    public RoundServiceImpl(RoundRepository roundRepository, PilotService pilotService) {
        this.roundRepository = roundRepository;
        this.pilotService = pilotService;
    }

    @Override
    public List<Round> findByPilotId(int pilotId) {
        return roundRepository.findByRoundIdPilotIdOrderByRoundIdRoundNum(pilotId);
    }

    @Override
    public List<Round> findByRoundNum(int roundNumber) {
        return roundRepository.findByRoundIdRoundNumOrderBySeconds(roundNumber);
    }

    @Override
    public ResponseEntity<String> save(Round round) {
        List<Round> rounds = findByRoundNum(round.getRoundId().getRoundNum());
        if (rounds.size() == 0) {
            roundRepository.save(round);
            return new ResponseEntity("Round saved!", HttpStatus.OK);
        }
        rounds.add(round);
        rounds = countResults(rounds);
        roundRepository.saveAll(rounds);
//            rounds.forEach(tempRound -> roundRepository.save(tempRound));

        return new ResponseEntity("Round saved, all scores recalculated", HttpStatus.OK);
    }

    @Override
    public List<Round> countResults(List<Round> rounds) {

        Float best = rounds.stream().min(Comparator.comparingDouble(Round::getSeconds)).get().getSeconds();
        rounds.forEach(tempRound -> tempRound.setScore(best / tempRound.getSeconds() * 1000));
        return rounds;
    }

    @Override
    public ResponseEntity<String> recalculateTotalScores(Integer roundId) {
        List<Pilot> pilotList = pilotService.findAll();
        List<Round> roundList = findByRoundNum(roundId);
        Map<Integer, Float> results = new HashMap<>();
        roundList.forEach(round -> results.put(round.getRoundId().getPilotId(), round.getScore()));
        pilotList.stream().filter(pilot -> results.containsKey(pilot.getId()))
                .forEach(pilot -> pilot.setScore(pilot.getScore() + results.entrySet().stream()
                        .filter(result ->
                                result.getKey().equals(pilot.getId()))
                        .findFirst().get().getValue()));
        pilotService.saveAll(pilotList);
        return new ResponseEntity<>("Scores recalculated successfully", HttpStatus.OK);
    }

    @Override
    public Round getBest(Integer pilotId) {
        List<Round> rounds = findByPilotId(pilotId);
        if (rounds.size() != 0) {
            return rounds.stream().max(Comparator.comparingDouble(Round::getScore)).get();
        } else return null;
    }
}
