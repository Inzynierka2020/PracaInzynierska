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





}
