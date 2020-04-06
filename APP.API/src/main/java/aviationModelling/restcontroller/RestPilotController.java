package aviationModelling.restcontroller;

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
    @GetMapping("/{eventId}")
    public List<PilotDTO> getPilots(@PathVariable Integer eventId) {
        return pilotService.findAll(eventId);
    }

    @ApiOperation(value = "Return all pilots, order by score")
    @GetMapping("/{eventId}/ranking")
    public List<PilotDTO> getPilotRanking(@PathVariable Integer eventId) {
        return pilotService.findAllOrderByScore(eventId);
    }

    @ApiOperation(value = "Update pilot")
    @PutMapping
    public ResponseEntity<PilotDTO> updatePilot(@RequestBody PilotDTO pilotDTO) {
        return pilotService.save(pilotDTO);
    }

    @ApiOperation(value = "Return pilot with the given id")
    @JsonView(Views.Public.class)
    @GetMapping("/{pilotId}/{eventId}")
    public PilotDTO getPilotById(@PathVariable int pilotId, @PathVariable Integer eventId) {
        return pilotService.findByPilotIdAndEventId(pilotId, eventId);
    }

    @ApiOperation(value = "Return all pilot flights with the given id")
    @GetMapping("/{pilotId}/{eventId}/flights")
    public List<FlightDTO> pilotFlights(@PathVariable int pilotId, @PathVariable Integer eventId) {
        return pilotService.findPilotFlights(pilotId, eventId);
    }

    @ApiOperation(value = "Return all completed pilot flights with the given id")
    @GetMapping("/{pilotId}/{eventId}/finished-flights")
    public List<FlightDTO> pilotFinishedFlights(@PathVariable int pilotId, @PathVariable Integer eventId) {
        return pilotService.findUncancelledAndFinishedPilotFlights(pilotId, eventId);
    }

    @ApiOperation(value = "Return all pilots who made their flight in the given round, " +
            "order by score desc, lastName")
    @GetMapping("/rounds/{roundNum}/{eventId}/finished-flights")
    public List<PilotDTO> getPilotsWithFinishedFlight(@PathVariable Integer roundNum, @PathVariable Integer eventId) {
        return pilotService.findPilotsWithFinishedFlight(roundNum, eventId);
    }

    @ApiOperation(value = "Return list of pilots who didn't make their flight in the " +
            "given round, order by lastName")
    @GetMapping("/rounds/{roundNum}/{eventId}/unfinished-flights")
    public List<PilotDTO> getPilotsWithUnfinishedFlight(@PathVariable Integer roundNum, @PathVariable Integer eventId) {
        return pilotService.findPilotsWithUnfinishedFlight(roundNum, eventId);
    }

    @ApiOperation(value = "Return list of pilots who made their flights in the given rounds and belong " +
            "to the given group, order by score desc, lastName")
    @GetMapping("/rounds/{roundNum}/{eventId}/finished-flights/{group}")
    public List<PilotDTO> getPilotsFromGroup(@PathVariable Integer roundNum, @PathVariable String group, @PathVariable Integer eventId) {
        return pilotService.findPilotsWithFinishedFlightGroupedByGroup(roundNum,group, eventId);
    }

    @ApiOperation(value = "Return pilot's best time")
    @GetMapping("/{pilotId}/{eventId}/best-time")
    public Float getPilotBestTime(@PathVariable Integer pilotId, @PathVariable Integer eventId) {
        return pilotService.findBestPilotTime(pilotId, eventId);
    }



}
