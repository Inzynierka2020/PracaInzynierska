package aviationModelling.service;

import aviationModelling.entity.Round;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoundService {

    List<Round> findByPilotId(int pilotId);
    List<Round> findByRoundNum(int roundNumber);
    ResponseEntity<String> save(Round round);
    ResponseEntity<String> saveAll(List<Round> roundList);

    ResponseEntity<String> recalculateTotalScores(Integer roundId);

    Round getBest(Integer pilotId);

    ResponseEntity<String> cancelRound(Integer round);
}
