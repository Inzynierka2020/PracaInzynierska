package aviationModelling.rest;

import aviationModelling.entity.Pilot;
import aviationModelling.service.PilotService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pilots")
public class RestPilotController {

    private PilotService pilotService;


    public RestPilotController(PilotService pilotService) {
        this.pilotService = pilotService;
    }

    @GetMapping("/list")
    public List<Pilot> getPilots() {
        return pilotService.findAll();
    }

    @GetMapping("/{pilotId}")
    public Pilot getPilotById(@PathVariable int pilotId) {
        return pilotService.findById(pilotId);
    }

    @PutMapping
    public Pilot updatePilot(@RequestBody Pilot pilot) {
        pilotService.save(pilot);
        return pilot;
    }






}
