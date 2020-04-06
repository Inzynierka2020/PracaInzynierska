package aviationModelling.restcontroller;

import aviationModelling.dto.FlightDTO;
import aviationModelling.service.FlightService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flights")
@CrossOrigin(origins = "*")
public class RestFlightController {

    private FlightService flightService;

    public RestFlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @ApiOperation(value = "Return single flight")
    @GetMapping("/{roundNum}/{pilotId}/{eventId}")
    public FlightDTO getFlight(@PathVariable Integer roundNum, @PathVariable Integer pilotId, @PathVariable Integer eventId) {
        return flightService.findFlight(roundNum, pilotId, eventId);
    }

    @ApiOperation(value = "Save flight")
    @PostMapping
    public ResponseEntity<FlightDTO> saveFlight(@RequestBody FlightDTO flightDTO) {
        return flightService.save(flightDTO);
    }

    @ApiOperation(value = "Update flight")
    @PutMapping
    public ResponseEntity<FlightDTO> updateFlight(@RequestBody FlightDTO flightDTO) {
        return flightService.save(flightDTO);
    }

    @ApiOperation(value = "Return flight with best time")
    @GetMapping("/best-time/{eventId}")
    public FlightDTO getBestFlight(@PathVariable Integer eventId) {
        return flightService.findBestTime(eventId);
    }
}
