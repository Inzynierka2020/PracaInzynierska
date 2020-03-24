package aviationModelling.service;

import aviationModelling.entity.Round;
import org.springframework.http.ResponseEntity;

public interface RoundService {

    void save(Round round);

    Round findByRoundNum(Integer roundNum);

    ResponseEntity<String> createRound(Integer roundNum);

    ResponseEntity<String> updateScore(Integer roundNum);

    Integer countRounds();

    ResponseEntity<String> cancelRound(Integer roundNum);

    ResponseEntity<String> updateGeneralScore(Integer roundNum);

}
