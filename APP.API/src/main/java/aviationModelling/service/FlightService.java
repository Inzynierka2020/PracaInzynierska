package aviationModelling.service;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.VaultResponseDTO;
import aviationModelling.entity.Flight;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FlightService {
    ResponseEntity<FlightDTO> save(FlightDTO flightDTO);
    ResponseEntity<VaultResponseDTO> postScore(FlightDTO flightDTO);
    ResponseEntity<?> delete(FlightDTO flightDTO);
//    ResponseEntity<List<FlightDTO>> saveAll(List<FlightDTO> flightList);
//    FlightDTO findBestTime(Integer eventId);
//
//    FlightDTO findFlight(Integer roundNum, Integer pilotId, Integer eventId);

}
