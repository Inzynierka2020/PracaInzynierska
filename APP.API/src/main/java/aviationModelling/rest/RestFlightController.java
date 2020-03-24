package aviationModelling.rest;

import aviationModelling.entity.Flight;
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

//    zapisz przelot i przelicz aktualny score kazdego pilota
    @PostMapping
    public ResponseEntity<String> saveFlight(@RequestBody Flight flight) {
        return flightService.save(flight);
    }

//    uaktualnij przelot
    @PutMapping
    public ResponseEntity<String> updateFlight(@RequestBody Flight flight) {
        return flightService.save(flight);
    }





}
