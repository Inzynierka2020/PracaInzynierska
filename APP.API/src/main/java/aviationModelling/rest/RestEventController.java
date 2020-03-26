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

//     zapisz informacje o evencie do bazy danych
    @PostMapping("/download-event-data/{eventId}")
    public ResponseEntity<String> saveEventFromVault(@PathVariable int eventId) {
        return eventService.saveEventDataFromVault(eventId);
    }

    //     zapisz informacje o pilotach do bazy danych
    @PostMapping("/download-pilots-data/{eventId}")
    public ResponseEntity<String> savePilotsFromVault(@PathVariable int eventId) {
        return eventService.savePilotsDataFromVault(eventId);
    }

//    uaktualnij total score
    @PutMapping("/update-total-score")
    public ResponseEntity<String> updateTotalScore() {
        return eventService.updateTotalScore();
    }
}