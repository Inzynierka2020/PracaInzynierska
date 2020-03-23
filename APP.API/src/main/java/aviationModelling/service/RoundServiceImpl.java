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
    public ResponseEntity<String> saveAll(List<Round> roundList) {
        roundRepository.saveAll(roundList);
        return new ResponseEntity<>("Round list saved successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> save(Round round) {
        List<Round> rounds = findByRoundNum(round.getRoundId().getRoundNum());
        if (rounds.size() == 0) {
            roundRepository.save(round);
            return new ResponseEntity("Round saved!", HttpStatus.OK);
        }
        rounds = updateScore(round, rounds);
        roundRepository.saveAll(rounds);
        return new ResponseEntity("Round saved, all scores recalculated", HttpStatus.OK);
    }

    private List<Round> updateScore(Round round, List<Round> rounds) {
        rounds.add(round);
        Float best = rounds.stream().min(Comparator.comparingDouble(Round::getSeconds)).get().getSeconds();
        rounds.forEach(tempRound -> tempRound.setScore(best / tempRound.getSeconds() * 1000));
        return rounds;

    }

    @Override
    public ResponseEntity<String> recalculateTotalScores(Integer roundId) {
        List<Pilot> pilotList = pilotService.findAll();
        if (!roundId.equals(4) && !roundId.equals(15)) {
            pilotList = recalculateNormal(roundId, pilotList);
        } else {
            pilotList = recalculateAfter4Or15Round(pilotList);
        }
        pilotService.saveAll(pilotList);
        return new ResponseEntity<>("Scores recalculated successfully", HttpStatus.OK);
    }

    private List<Pilot> recalculateAfter4Or15Round(List<Pilot> pilotList) {

        for (Pilot pilot : pilotList) {
            List<Round> pilotsRounds = findByPilotId(pilot.getId());
            if (pilotsRounds.size() == 0) {
                pilot.setScore(0F);
                continue;
            }
//            wybierz najgorszy wynik sposrod nieodrzuconych
            pilotsRounds.stream()
                    .filter(round -> !round.isDiscarded() && !round.isCancelled())
                    .min(Comparator.comparingDouble(Round::getScore))
                    .get()
                    .setDiscarded(true);
//
            Float totalScore = 0F;
            for (Round round : pilotsRounds) {
                if (round.isDiscarded() == false) {
                    totalScore += round.getScore();
                }
            }
            pilot.setScore(totalScore);
        }
        return pilotList;
    }

    private List<Pilot> recalculateNormal(Integer roundId, List<Pilot> pilotList) {
        List<Round> roundList = findByRoundNum(roundId);
        Map<Integer, Float> results = new HashMap<>();
//        utwórz mapę z id_pilota i ich wynikami z ostatniej rundy
        roundList.stream()
                .filter(round -> round.isDiscarded() == false)  //wez nieodrzucone wyniki
                .forEach(round -> results.put(round.getRoundId().getPilotId(), round.getScore() - round.getPenalty()));
//        if (w results key == id_pilota) => pilot.setScore(wynik - kara)
        pilotList.stream()
                .filter(pilot -> results.containsKey(pilot.getId()))    //
                .forEach(pilot -> pilot.setScore(pilot.getScore() +
                        results.entrySet().stream()
                                .filter(result -> result.getKey().equals(pilot.getId()))
                                .findFirst().get().getValue()));
        return pilotList;
    }

    @Override
    public Round getBest(Integer pilotId) {
        List<Round> rounds = findByPilotId(pilotId);
        if (rounds.size() != 0) {
            return rounds.stream().max(Comparator.comparingDouble(Round::getScore)).get();
        } else return null;
    }

    @Override
    public ResponseEntity<String> cancelRound(Integer round) {
        List<Round> rounds = findByRoundNum(round);
        rounds.forEach(tempRound -> tempRound.setCancelled(true));
        saveAll(rounds);
        return new ResponseEntity<>("Round "+round+" was cancelled", HttpStatus.OK);
    }
}
