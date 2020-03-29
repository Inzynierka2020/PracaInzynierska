package aviationModelling.rest;

import aviationModelling.dto.FlightDTO;
import aviationModelling.entity.Flight;
import aviationModelling.mapper.FlightMapper;
import aviationModelling.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class RestFlightController {

    private FlightService flightService;

    public RestFlightController(FlightService flightService) {
        this.flightService = flightService;
    }

//    znajdz pojedynczy lot
    @GetMapping("/{roundNum}/{pilotId}")
    public FlightDTO getFlight(@PathVariable Integer roundNum, @PathVariable Integer pilotId) {
        return FlightMapper.MAPPER.toFlightDTO(flightService.findFlight(roundNum, pilotId));
    }

//    zapisz przelot
    @PostMapping
    public ResponseEntity<String> saveFlight(@RequestBody Flight flight) {
        return flightService.save(flight);
    }

//    uaktualnij przelot
    @PutMapping
    public ResponseEntity<String> updateFlight(@RequestBody Flight flight) {
        return flightService.save(flight);
    }

//    zwroc najszybszy lot
    @GetMapping("/best-time")
    public FlightDTO getBestFlight() {
        return FlightMapper.MAPPER.toFlightDTO(flightService.findBestTime());
    }
}
