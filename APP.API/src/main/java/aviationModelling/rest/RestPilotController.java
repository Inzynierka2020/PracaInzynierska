package aviationModelling.rest;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.PilotDTO;
import aviationModelling.dto.Views;
import aviationModelling.service.PilotService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pilots")
@CrossOrigin(origins = "*")
public class RestPilotController {

    private PilotService pilotService;

    public RestPilotController(PilotService pilotService) {
        this.pilotService = pilotService;
    }

    @ApiOperation(value = "Return all pilots, order by lastName")
    @GetMapping
    public List<PilotDTO> getPilots() {
        return pilotService.findAll();
    }

    @ApiOperation(value = "Return all pilots, order by score")
    @GetMapping("/ranking")
    public List<PilotDTO> getPilotRanking() {
        return pilotService.findAllOrderByScore();
    }

    @ApiOperation(value = "Update pilot")
    @PutMapping
    public ResponseEntity<PilotDTO> updatePilot(@RequestBody PilotDTO pilotDTO) {
        return pilotService.save(pilotDTO);
    }

    @ApiOperation(value = "Return pilot with the given id")
    @JsonView(Views.Internal.class)
    @GetMapping("/{pilotId}")
    public PilotDTO getPilotById(@PathVariable int pilotId) {
        return pilotService.findById(pilotId);
    }

    @ApiOperation(value = "Return all pilot flights with the given id")
    @GetMapping("/{pilotId}/flights")
    public List<FlightDTO> pilotFlights(@PathVariable int pilotId) {
        return pilotService.findPilotFlights(pilotId);
    }

    @ApiOperation(value = "Return all completed pilot flights with the given id")
    @GetMapping("/{pilotId}/finished-flights")
    public List<FlightDTO> pilotFinishedFlights(@PathVariable int pilotId) {
        return pilotService.findUncancelledAndFinishedPilotFlights(pilotId);
    }

    @ApiOperation(value = "Return all pilots who made their flight in the given round, " +
            "order by score desc, lastName")
    @GetMapping("/rounds/{roundNum}/finished-flights")
    public List<PilotDTO> getPilotsWithFinishedFlight(@PathVariable Integer roundNum) {
        return pilotService.findPilotsWithFinishedFlight(roundNum);
    }

    @ApiOperation(value = "Return list of pilots who didn't make their flight in the " +
            "given round, order by lastName")
    @GetMapping("/rounds/{roundNum}/unfinished-flights")
    public List<PilotDTO> getPilotsWithUnfinishedFlight(@PathVariable Integer roundNum) {
        return pilotService.findPilotsWithUnfinishedFlight(roundNum);
    }

    @ApiOperation(value = "Return list of pilots who made their flights in the given rounds and belong " +
            "to the given group, order by score desc, lastName")
    @GetMapping("/rounds/{roundNum}/finished-flights/{group}")
    public List<PilotDTO> getPilotsFromGroup(@PathVariable Integer roundNum, @PathVariable String group) {
        return pilotService.findPilotsWithFinishedFlightGroupedByGroup(roundNum,group);
    }

    @ApiOperation(value = "Return pilot with best time")
    @GetMapping("/best-time/{pilotId}")
    public Float getBestTime(@PathVariable Integer pilotId) {
        return pilotService.findBestPilotTime(pilotId);
    }



}
