package aviationModelling.rest;

import aviationModelling.dto.FlightDTO;
import aviationModelling.entity.Flight;
import aviationModelling.mapper.FlightMapper;
import aviationModelling.service.FlightService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
@CrossOrigin(origins = "*")
public class RestFlightController {

    private FlightService flightService;

    public RestFlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @ApiOperation(value = "Return single flight")
    @GetMapping("/{roundNum}/{pilotId}")
    public FlightDTO getFlight(@PathVariable Integer roundNum, @PathVariable Integer pilotId) {
        return flightService.findFlight(roundNum, pilotId);
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
    @GetMapping("/best-time")
    public FlightDTO getBestFlight() {
        return flightService.findBestTime();
    }
}
