package aviationModelling.service;

import aviationModelling.dto.EventDataDTO;
import aviationModelling.entity.Event;
import aviationModelling.entity.Pilot;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class VaultParser {

    private UrlWizard urlWizard;

    public VaultParser(UrlWizard urlWizard) {
        this.urlWizard = urlWizard;
    }

//      Pilot ID, Pilot Bib, Pilot First Name, Pilot Last Name, Class, AMA, FAI, FAI License, Team Name

    public List<Pilot> retrievePilotList(int eventId) {
        RestTemplate restTemplate = new RestTemplate();
        String text = restTemplate.getForObject(urlWizard.getEventInfo(eventId), String.class);
        List<String> eventInfo = readFileAsLines(text);

        if (eventInfo.get(0).equals("0")) {
            return null;
        }

        List<Pilot> pilotList = new ArrayList<>();

        for (int i = 3; i < eventInfo.size(); i++) {
            Pilot pilot = new Pilot();
            String[] line = eventInfo.get(i).split(",");
            pilot.setId(Integer.parseInt(line[0].trim()));
            pilot.setPilotBib(line[1].trim());
            pilot.setFirstName(line[2].trim());
            pilot.setLastName(line[3].trim());
            pilot.setPilotClass(line[4].trim());
            pilot.setAma(line[5].trim());
            pilot.setFai(line[6].trim());
            pilot.setFaiLicence(line[7].trim());
            pilot.setTeamName(line[8].trim());
            pilot.setEventId(eventId);
            pilotList.add(pilot);
        }
        return pilotList;
    }

//      Event ID, Event Name, Event_location, Event Start Date, Event End Date, Event Type, Number of Rounds

    public Event retrieveEventInfo(int eventId) {
        RestTemplate restTemplate = new RestTemplate();
        String text = restTemplate.getForObject(urlWizard.getEventInfo(eventId), String.class);

        List<String> eventInfo = readFileAsLines(text);

        if (eventInfo.get(0).equals("0")) {
            return null;
        }

        Event event = new Event();

        String[] cols = eventInfo.get(1).split(",");
        event.setEventId(Integer.parseInt(cols[0].trim()));
        event.setEventName(cols[1].trim());
        event.setEventLocation(cols[2].trim());
        event.setEventStartDate(cols[3].trim());
        event.setEventEndDate(cols[4].trim());
        event.setEventType(cols[5].trim());
        event.setNumberOfRounds(Integer.parseInt(cols[6].trim()));

        return event;
    }

    public List<String> readFileAsLines(String list) {

        return Arrays.asList(list.replace("\"\"", " ")
                .replace("\"", "").split("\n"));
    }
}
