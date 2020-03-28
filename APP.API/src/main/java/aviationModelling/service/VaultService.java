package aviationModelling.service;

import aviationModelling.dto.VaultEventDataDTO;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VaultService {

    private UrlWizard urlWizard;
    private RestTemplate restTemplate;

    public VaultService(UrlWizard urlWizard) {
        this.urlWizard = urlWizard;
        this.restTemplate = new RestTemplate();
    }

    public VaultEventDataDTO retrieveEventData(int eventId) {
        String text = restTemplate.getForObject(urlWizard.getEventInfo(eventId), String.class);
        text = text.replace("[]", "null").replace("\"\"", "null");

        VaultEventDataDTO eventData = new Gson().fromJson(text, VaultEventDataDTO.class);
        eventData.getEvent().setEvent_id(eventId);

//        przypisz ID pilota do przelotow
        eventData.getEvent().getPilots().forEach(pilot -> {
            pilot.setEvent_id(eventId);
            pilot.getFlights().forEach(flight -> {
                flight.setPilot_id(pilot.getPilot_id());
                flight.setEvent_id(eventId);
            });
        });

        return eventData;
    }


}
