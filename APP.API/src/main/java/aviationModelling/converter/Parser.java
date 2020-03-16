package aviationModelling.converter;

import aviationModelling.entity.Event;
import aviationModelling.entity.Pilot;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Parser {

//      getEventInfo function
//      Pilot ID, Pilot Bib, Pilot First Name, Pilot Last Name, Class, AMA, FAI, FAI License, Team Name
    public static List<Pilot> getPilotList(String stringList, int eventId) {
        List<String> lines = readFileAsLines(stringList);
        List<Pilot> pilotList = new ArrayList<>();
        if (lines.get(0).equals("0")) {
            return null;
        }
        for (int i = 3; i < lines.size(); i++) {
            Pilot tmpPilot = new Pilot();
            String[] line = lines.get(i).split(",");
            tmpPilot.setId(Integer.parseInt(line[0]));
            tmpPilot.setPilotBib(line[1]);
            tmpPilot.setFirstName(line[2]);
            tmpPilot.setLastName(line[3]);
            tmpPilot.setPilotClass(line[4]);
            tmpPilot.setAma(line[5]);
            tmpPilot.setFai(line[6]);
            tmpPilot.setFaiLicence(line[7]);
            tmpPilot.setTeamName(line[8]);
            tmpPilot.setEventId(eventId);
            tmpPilot.setRounds(null);
            pilotList.add(tmpPilot);
        }
        return pilotList;
    }
//      getEventInfo function
//      Event ID, Event Name, Event_location, Event Start Date, Event End Date, Event Type, Number of Rounds
    public static Event getEventInfo(String stringList) {
        List<String> lines = readFileAsLines(stringList);
        Event event = new Event();
        if (lines.get(0).equals("0")) {
            return null;
        }

        String[] cols = lines.get(1).split(",");
        event.setEventId(Integer.parseInt(cols[0]));
        event.setEventName(cols[1]);
        event.setEventLocation(cols[2]);
        event.setEventStartDate(cols[3]);
        event.setEventEndDate(cols[4]);
        event.setEventType(cols[5]);
        event.setNumberOfRounds(Integer.parseInt(cols[6]));
        event.setPilots(null);

        return event;
    }

    public static List<String> readFileAsLines(String list) {

        return Arrays.asList(list.replace("\"\"", " ")
                .replace("\"", "").split("\n"));
    }
}
