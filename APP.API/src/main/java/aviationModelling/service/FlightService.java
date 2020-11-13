package aviationModelling.service;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.VaultResponseDTO;
import aviationModelling.entity.Flight;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FlightService {
    ResponseEntity<FlightDTO> save(FlightDTO flightDTO);
    ResponseEntity<VaultResponseDTO> postScore(Integer roundNum, Integer pilotId, Integer eventId);
    ResponseEntity<?> delete(FlightDTO flightDTO);
    Flight findFlight(Integer roundNum, Integer pilotId, Integer eventId);
    Integer findHighestValidRoundNumber(Integer eventId);

}
