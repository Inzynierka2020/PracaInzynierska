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

//    wyswietl wszystkie loty w danej rundzie
    @GetMapping("/rounds/{roundNum}")
    public List<Flight> getSpecificRound(@PathVariable int roundNum) {
        List<Flight> flightList = flightService.findByRoundNum(roundNum);
        return flightList;
    }

//      wyswietl wszystkie rundy danego pilota
    @GetMapping("/pilots/{pilot_id}")
    public List<Flight> getPilotRounds(@PathVariable int pilot_id) {
        List<Flight> flightList = flightService.findByPilotId(pilot_id);
        return flightList;
    }

    @PostMapping
    public ResponseEntity<String> saveFlight(@RequestBody Flight flight) {
        return flightService.save(flight);
    }

    @PutMapping
    public ResponseEntity<String> updateFlight(@RequestBody Flight flight) {
        return flightService.save(flight);
    }

    @PutMapping("/finish/{roundNum}")
    public ResponseEntity<String> finishFlight(@PathVariable Integer roundNum) {
        return flightService.recalculateTotalScores(roundNum);
    }

    @GetMapping("/best/{pilotId}")
    public Flight getBest(@PathVariable Integer pilotId) {
        return flightService.getBest(pilotId);
    }

    @PutMapping("/cancel/{roundNum}")
    public ResponseEntity<String> cancelRound(@PathVariable Integer roundNum) {
        return flightService.cancelRound(roundNum);
    }




}
