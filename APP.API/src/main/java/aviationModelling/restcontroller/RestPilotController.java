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
@CrossOrigin(origins = "http://localhost:4200")
public class RestPilotController {

    private PilotService pilotService;

    public RestPilotController(PilotService pilotService) {
        this.pilotService = pilotService;
    }

    @ApiOperation(value = "Return all pilots, order by lastName")
    @JsonView(Views.Public.class)
    @GetMapping
    public List<PilotDTO> getPilots(@RequestParam Integer eventId) {
        return pilotService.findAll(eventId);
    }
}
