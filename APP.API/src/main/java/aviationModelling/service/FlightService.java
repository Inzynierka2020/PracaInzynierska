package aviationModelling.service;

import aviationModelling.entity.Flight;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FlightService {
    ResponseEntity<String> save(Flight flight);
}
