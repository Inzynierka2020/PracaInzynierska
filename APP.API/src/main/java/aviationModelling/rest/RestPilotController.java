package aviationModelling.rest;

import aviationModelling.entity.Pilot;
import aviationModelling.service.PilotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pilots")
public class RestPilotController {

    private PilotService pilotService;


    public RestPilotController(PilotService pilotService) {
        this.pilotService = pilotService;
    }

//    wyswietl wszystkich pilotw (kolejnosc alfabetyczna)
    @GetMapping
    public List<Pilot> getPilots() {
        return pilotService.findAll();
    }

    @GetMapping("/{pilotId}")
    public Pilot getPilotById(@PathVariable int pilotId) {
        return pilotService.findById(pilotId);
    }

    @PutMapping
    public ResponseEntity<String> updatePilot(@RequestBody Pilot pilot) {
        return pilotService.save(pilot);
    }

    @GetMapping("finished-flight/{roundNum}")
    public List<Pilot> getPilotsWithFinishedFlight(@PathVariable Integer roundNum) {
        return pilotService.findPilotsWithFinishedFlight(roundNum);
    }

    @GetMapping("unfinished-flight/{roundNum}")
    public List<Pilot> getPilotsWithUnfinishedFlight(@PathVariable Integer roundNum) {
        return pilotService.findPilotsWithUnfinishedFlight(roundNum);
    }

    @GetMapping("/finished-flight-group-by-group/round={roundNum}&group={group}")
    public List<Pilot> getPilotsFromGroup(@PathVariable Integer roundNum, @PathVariable String group) {
        return pilotService.findPilotsWithFinishedFlightGroupedByGroup(roundNum,group);
    }

}
