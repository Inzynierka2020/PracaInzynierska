package aviationModelling.rest;

import aviationModelling.dto.EventDTO;
import aviationModelling.exception.CustomResponse;
import aviationModelling.mapper.EventMapper;
import aviationModelling.service.EventService;
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

    //    pobierz z lokalnej bazy event o podanym id
    @GetMapping("/{eventId}")
    public EventDTO getEvent(@PathVariable int eventId) {
        return eventService.findById(eventId);
    }

//     zapisz informacje o evencie do bazy danych
    @PostMapping("/event-data/{eventId}")
    public ResponseEntity<CustomResponse> saveEventFromVault(@PathVariable int eventId) {
        return eventService.initializeDbWithDataFromVault(eventId);
    }
    

//    uaktualnij total score
    @PutMapping("/total-score")
    public ResponseEntity<CustomResponse> updateTotalScore() {
        return eventService.updateTotalScore();
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<CustomResponse> deleteEvent(@PathVariable int eventId) {
        return eventService.delete(eventId);
    }
}