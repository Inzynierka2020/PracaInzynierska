package aviationModellingApp.rest;

import aviationModellingApp.converter.Parser;
import aviationModellingApp.converter.UrlWizard;
import aviationModellingApp.entity.Event;
import aviationModellingApp.entity.Pilot;
import aviationModellingApp.service.EventService;
import aviationModellingApp.service.PilotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


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
    @GetMapping("/saveEvent/{eventId}")
    public boolean saveEventAndPilots(@PathVariable int eventId) {
        return eventService.saveEventAndPilots(eventId);
    }
}