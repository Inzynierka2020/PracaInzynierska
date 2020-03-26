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

    ResponseEntity<String> updateLocalScore(Integer roundNum);

    ResponseEntity<String> cancelRound(Integer roundNum);

    List<Flight> findRoundFlights(Integer roundNum);

    List<Flight> findUncancelledRoundFlights(Integer roundNum);


    ResponseEntity<String> finishRound(Integer roundNum);
}
