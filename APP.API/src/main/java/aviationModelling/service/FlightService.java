package aviationModelling.service;

import aviationModelling.entity.Flight;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FlightService {

    List<Flight> findByPilotId(int pilotId);
    List<Flight> findByRoundNum(int roundNum);
    ResponseEntity<String> save(Flight flight);
    ResponseEntity<String> saveAll(List<Flight> flightList);

    ResponseEntity<String> recalculateTotalScores(Integer roundNum);

    Flight getBest(Integer pilotId);

    ResponseEntity<String> cancelRound(Integer roundNum);
}
