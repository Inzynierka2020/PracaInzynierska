package aviationModelling.service;

import aviationModelling.dto.EventDTO;
import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.PilotDTO;
import aviationModelling.dto.VaultEventDataDTO;
import aviationModelling.entity.Event;
import aviationModelling.mapper.EventMapper;
import aviationModelling.mapper.FlightMapper;
import aviationModelling.mapper.PilotMapper;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class VaultService {

    private UrlWizard urlWizard;
    private RestTemplate restTemplate;

    public VaultService(UrlWizard urlWizard) {
        this.urlWizard = urlWizard;
        this.restTemplate = new RestTemplate();
    }


    public Event retrieveEventData(int eventId) {
        String text = restTemplate.getForObject(urlWizard.getEventInfo(eventId), String.class);
        text = text.replace("[]", "null").replace("\"\"", "null");
        VaultEventDataDTO eventData = new Gson().fromJson(text, VaultEventDataDTO.class);

        Event event = EventMapper.MAPPER.toEvent(eventData.getEvent());
        event.setEventId(eventId);
        event.setPilots(null);







        return event;
    }



}
