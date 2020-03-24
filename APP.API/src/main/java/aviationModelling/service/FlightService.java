package aviationModelling.service;

import aviationModelling.entity.Flight;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FlightService {

    List<Flight> findByPilotId(int pilotId);
    List<Flight> findByRoundNum(int roundNum);
    ResponseEntity<String> save(Flight flight);
    ResponseEntity<String> saveAll(List<Flight> flightList);


    Float getBestScore(Integer pilotId);
    Float getBestTime(Integer pilotId);


}
