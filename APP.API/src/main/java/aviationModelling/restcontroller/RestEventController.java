package aviationModelling.restcontroller;

import aviationModelling.dto.EventDTO;
import aviationModelling.dto.Views;
import aviationModelling.exception.CustomResponse;
import aviationModelling.service.EventService;
import com.fasterxml.jackson.annotation.JsonView;
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

    @ApiOperation(value = "Return event")
    @JsonView(Views.Internal.class)
    @GetMapping("/{eventId}")
    public EventDTO getEvent(@PathVariable int eventId) {
        return eventService.getEvent(eventId);
    }

    @ApiOperation(value = "Download data from F3XVault and save it to local db")
    @PostMapping("/event-data/{eventId}")
    public ResponseEntity<CustomResponse> saveEventFromVault(@PathVariable int eventId) {
        return eventService.initializeDbWithDataFromVault(eventId);
    }
//
//
//    @ApiOperation(value = "Update total score")
//    @PutMapping("/total-score/{eventId}")
//    public ResponseEntity<CustomResponse> updateTotalScore(@PathVariable int eventId) {
//        return eventService.updateTotalScore(eventId);
//    }
//
//    @ApiOperation(value = "Delete event")
//    @DeleteMapping("/{eventId}")
//    public ResponseEntity<CustomResponse> deleteEvent(@PathVariable int eventId) {
//        return eventService.delete(eventId);
//    }
}