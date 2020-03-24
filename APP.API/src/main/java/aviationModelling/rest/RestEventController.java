package aviationModelling.rest;

import aviationModelling.entity.Event;
import aviationModelling.service.EventService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class RestEventController {

    private EventService eventService;

    public RestEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{eventId}")
    public Event getEvent(@PathVariable int eventId) {
        return eventService.findById(eventId);
    }

//     zapisz informacje o evencie oraz pilotow do bazy danych
    @GetMapping("/save-event/{eventId}")
    public void saveEventAndPilots(@PathVariable int eventId) {
        eventService.saveEventAndPilots(eventId);
    }
}