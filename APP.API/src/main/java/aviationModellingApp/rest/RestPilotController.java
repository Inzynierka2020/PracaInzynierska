package aviationModellingApp.rest;

import aviationModellingApp.converter.Parser;
import aviationModellingApp.converter.UrlWizard;
import aviationModellingApp.entity.Event;
import aviationModellingApp.entity.Pilot;
import aviationModellingApp.service.PilotService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
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
