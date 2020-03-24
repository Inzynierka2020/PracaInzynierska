package aviationModelling.rest;

import aviationModelling.entity.Pilot;
import aviationModelling.service.PilotService;
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

    @GetMapping("finished-flight/{round}")
    public List<Pilot> getPilotsWithFinishedFlight(@PathVariable Integer round) {
        return pilotService.findPilotsWithFinishedFlight(round);
    }

    @GetMapping("unfinished-flight/{round}")
    public List<Pilot> getPilotsWithUnfinishedFlight(@PathVariable Integer round) {
        return pilotService.findPilotsWithUnfinishedFlight(round);
    }


    @GetMapping("/finished-flight-group-by-group/round={round}&group={group}")
    public List<Pilot> getPilotsFromGroup(@PathVariable Integer round, @PathVariable String group) {
        return pilotService.findPilotsWithFinishedFlightGroupedByGroup(round,group);
    }

}
