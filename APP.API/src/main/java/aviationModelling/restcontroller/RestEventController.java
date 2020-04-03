package aviationModelling.restcontroller;

import aviationModelling.dto.EventDTO;
import aviationModelling.exception.CustomResponse;
import aviationModelling.service.EventService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "*")
public class RestEventController {

    private EventService eventService;

    public RestEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @ApiOperation(value = "Return event with the given id")
    @GetMapping("/{eventId}")
    public EventDTO getEvent(@PathVariable int eventId) {
        return eventService.findById(eventId);
    }

    @ApiOperation(value = "Download data from F3XVault and save it to local db")
    @PostMapping("/event-data/{eventId}")
    public ResponseEntity<CustomResponse> saveEventFromVault(@PathVariable int eventId) {
        return eventService.initializeDbWithDataFromVault(eventId);
    }


    @ApiOperation(value = "Update total score")
    @PutMapping("/total-score")
    public ResponseEntity<CustomResponse> updateTotalScore() {
        return eventService.updateTotalScore();
    }

    @ApiOperation(value = "Delete event with the given id")
    @DeleteMapping("/{eventId}")
    public ResponseEntity<CustomResponse> deleteEvent(@PathVariable int eventId) {
        return eventService.delete(eventId);
    }
}