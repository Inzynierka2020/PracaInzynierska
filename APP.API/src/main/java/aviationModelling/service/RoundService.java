package aviationModelling.service;

import aviationModelling.entity.Flight;
import aviationModelling.entity.Round;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoundService {

    void save(Round round);

    Round findByRoundNum(Integer roundNum);

    ResponseEntity<String> createRound(Integer roundNum, Integer eventId);

    ResponseEntity<String> updateLocalScore(Integer roundNum);

    ResponseEntity<String> cancelRound(Integer roundNum);

    List<Flight> findRoundFlights(Integer roundNum);

    List<Flight> findUncancelledRoundFlights(Integer roundNum);


    ResponseEntity<String> finishRound(Integer roundNum);

    List<Integer> getRoundNumbers();

    ResponseEntity<String> updateAllRounds();

    List<Round> findAll();
}
