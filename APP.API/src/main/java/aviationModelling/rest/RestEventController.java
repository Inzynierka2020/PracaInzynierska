package aviationModelling.rest;

import aviationModelling.entity.Event;
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
    public Event getEvent(@PathVariable int eventId) {
        return eventService.findById(eventId);
    }

//     zapisz informacje o evencie oraz pilotow do bazy danych
    @PostMapping("/save-event/{eventId}")
    public ResponseEntity<String> saveEventAndPilots(@PathVariable int eventId) {
        return eventService.saveEventAndPilotsFromVault(eventId);
    }
}