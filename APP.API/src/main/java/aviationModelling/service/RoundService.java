package aviationModelling.service;

import aviationModelling.entity.Flight;
import aviationModelling.entity.Round;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoundService {

    void save(Round round);

    Round findByRoundNum(Integer roundNum);

    ResponseEntity<String> createRound(Integer roundNum);

    ResponseEntity<String> updateScore(Integer roundNum);

    Integer countRounds();

    ResponseEntity<String> cancelRound(Integer roundNum);

    ResponseEntity<String> updateGeneralScore(Integer roundNum);

    List<Flight> findRoundFlights(Integer roundNum);

}
