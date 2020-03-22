package aviationModelling.service;

import aviationModelling.entity.Round;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoundService {

    List<Round> findByPilotId(int pilotId);
    List<Round> findByRoundNum(int roundNumber);
    ResponseEntity<String> save(Round round);
    List<Round> countResults(List<Round> rounds);

    ResponseEntity<String> recalculateTotalScores(Integer roundId);

    Round getBest(Integer pilotId);
}
