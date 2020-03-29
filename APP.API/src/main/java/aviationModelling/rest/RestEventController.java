package aviationModelling.rest;

import aviationModelling.dto.EventDTO;
import aviationModelling.entity.Event;
import aviationModelling.mapper.EventMapper;
import aviationModelling.service.EventService;
import aviationModelling.service.VaultService;
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
        return EventMapper.MAPPER.toEventDTO(eventService.findById(eventId));
    }

//     zapisz informacje o evencie do bazy danych
    @PostMapping("/event-data/{eventId}")
    public ResponseEntity<String> saveEventFromVault(@PathVariable int eventId) {
        return eventService.initializeDbWithDataFromVault(eventId);
    }

//    //     zapisz informacje o pilotach do bazy danych
//    @PostMapping("/pilots-data/{eventId}")
//    public ResponseEntity<String> savePilotsFromVault(@PathVariable int eventId) {
//        return eventService.savePilotsDataFromVault(eventId);
//    }

//    uaktualnij total score
    @PutMapping("/total-score")
    public ResponseEntity<String> updateTotalScore() {
        return eventService.updateTotalScore();
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable int eventId) {
        return eventService.delete(eventId);
    }
}