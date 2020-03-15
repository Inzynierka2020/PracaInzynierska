package aviationModellingApp.service;

import aviationModellingApp.converter.Parser;
import aviationModellingApp.converter.UrlWizard;
import aviationModellingApp.entity.Event;
import aviationModellingApp.entity.Pilot;
import aviationModellingApp.repository.EventRepository;
import aviationModellingApp.repository.PilotRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private PilotService pilotService;
    private UrlWizard urlWizard;

    public EventServiceImpl(EventRepository eventRepository, PilotService pilotService, UrlWizard urlWizard) {
        this.eventRepository = eventRepository;
        this.pilotService = pilotService;
        this.urlWizard = urlWizard;
    }

    @Override
    public Event findById(int id) {
        Optional<Event> result = eventRepository.findById(id);

        Event event = null;

        if (result.isPresent()) {
            event = result.get();
        } else {
            throw new RuntimeException("Didn't find event with id = " + id);
        }
        return event;
    }

    @Override
    public void save(Event event) {
        eventRepository.save(event);
    }

    public boolean saveEventAndPilots(int eventId) {
//        zbuduj odpowiedni adres url do vaulta
        try {
            String url = urlWizard.getEventInfo(eventId);

//        pobierz odpowiedz na zapytanie GET jako String (tak zwraca Vault ;/)
            RestTemplate restTemplate = new RestTemplate();
            String pilotStringList = restTemplate.getForObject(url, String.class);

//        parsuj Stringa, aby otrzymac obiekt event i zapisz go do bazy
            Event event = Parser.getEventInfo(pilotStringList, eventId);
            eventRepository.save(event);

//        parsuj Stringa, aby otrzymac liste pilotow z zawodow i zapisz ich do bazy
            List<Pilot> pilotList = Parser.getPilotList(pilotStringList, eventId);
            pilotService.savePilots(pilotList);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
