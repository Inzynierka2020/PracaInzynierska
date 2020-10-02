package aviationModelling.restcontroller;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.VaultResponseDTO;
import aviationModelling.service.FlightService;
import aviationModelling.service.VaultService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flights")
public class RestFlightController {

    private FlightService flightService;
    private VaultService vaultService;

    public RestFlightController(FlightService flightService, VaultService vaultService) {
        this.flightService = flightService;
        this.vaultService = vaultService;
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

    @ApiOperation(value = "Send flight to F3XVault")
    @PostMapping("/vault")
    public ResponseEntity<VaultResponseDTO> saveFlightToVault(@RequestBody FlightDTO flightDTO) {
        return flightService.postScore(flightDTO);
    }

    @ApiOperation(value = "Delete flight from db")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFlight(@RequestBody FlightDTO flightDTO) {
        return flightService.delete(flightDTO);
    }

    //
//    @ApiOperation(value = "Return single flight")
//    @GetMapping
//    public FlightDTO getFlight(@RequestParam Integer roundNum, @RequestParam Integer pilotId, @RequestParam Integer eventId) {
//        return flightService.findFlight(roundNum, pilotId, eventId);
//    }
//

//    @ApiOperation(value = "Return flight with best time")
//    @GetMapping("/best-time")
//    public FlightDTO getBestFlight(@RequestParam Integer eventId) {
//        return flightService.findBestTime(eventId);
//    }
}
