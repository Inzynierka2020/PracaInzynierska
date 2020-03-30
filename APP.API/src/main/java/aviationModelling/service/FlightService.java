package aviationModelling.service;

import aviationModelling.dto.FlightDTO;
import aviationModelling.entity.Flight;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FlightService {
    ResponseEntity<String> save(FlightDTO flightDTO);
    ResponseEntity<String> saveAll(List<Flight> flightList);
    Flight findBestTime();

    Flight findFlight(Integer roundNum, Integer pilotId);

}
