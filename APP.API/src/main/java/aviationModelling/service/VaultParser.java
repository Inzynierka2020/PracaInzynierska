package aviationModelling.service;

import aviationModelling.entity.Event;
import aviationModelling.entity.Pilot;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class VaultParser {

    private UrlWizard urlWizard;
    private List<String> eventInfo;

    public VaultParser(UrlWizard urlWizard) {
        this.urlWizard = urlWizard;
        RestTemplate restTemplate = new RestTemplate();
        String text = restTemplate.getForObject(urlWizard.getEventInfo(1809), String.class);
        eventInfo = readFileAsLines(text);
    }

//      Pilot ID, Pilot Bib, Pilot First Name, Pilot Last Name, Class, AMA, FAI, FAI License, Team Name

    public List<Pilot> getPilotList(int eventId) {
        List<Pilot> pilotList = new ArrayList<>();

        if (eventInfo.get(0).equals("0")) {
            return null;
        }
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
            pilot.setScore((float) 0);
            pilot.setFlights(null);
            pilotList.add(pilot);
        }
        return pilotList;
    }
//      Event ID, Event Name, Event_location, Event Start Date, Event End Date, Event Type, Number of Rounds

    public Event getEventInfo() {
        Event event = new Event();
        if (eventInfo.get(0).equals("0")) {
            return null;
        }

        String[] cols = eventInfo.get(1).split(",");
        event.setEventId(Integer.parseInt(cols[0].trim()));
        event.setEventName(cols[1].trim());
        event.setEventLocation(cols[2].trim());
        event.setEventStartDate(cols[3].trim());
        event.setEventEndDate(cols[4].trim());
        event.setEventType(cols[5].trim());
        event.setNumberOfRounds(Integer.parseInt(cols[6].trim()));
        event.setPilots(null);

        return event;
    }

    public List<String> readFileAsLines(String list) {

        return Arrays.asList(list.replace("\"\"", " ")
                .replace("\"", "").split("\n"));
    }
}
