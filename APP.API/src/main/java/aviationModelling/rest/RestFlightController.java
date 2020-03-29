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

//    znajdz pojedynczy lot
    @GetMapping("/{roundNum}/{pilotId}")
    public Flight getFlight(@PathVariable Integer roundNum, @PathVariable Integer pilotId) {
        return flightService.findFlight(roundNum, pilotId);
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
    public Flight getBestFlight() {
        return flightService.findBestTime();
    }

//    zwroc najlepiej punktowany lot
    @GetMapping("/best-score")
    public Flight getBestScore() {
        return flightService.findBestScore();
    }




}
