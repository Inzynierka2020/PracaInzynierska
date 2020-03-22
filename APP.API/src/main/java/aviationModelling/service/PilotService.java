package aviationModelling.service;

import aviationModelling.entity.Pilot;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PilotService {

    List<Pilot> findAll();
    void savePilots(List<Pilot> pilots);
    ResponseEntity<String> save(Pilot pilot);
    ResponseEntity<String> saveAll(List<Pilot> pilotList);

    Pilot findById(int id);
    List<Pilot> findPilotsWithFinishedFlight(Integer roundNum);
    List<Pilot> findPilotsWithUnfinishedFlight(Integer roundNum);
    List<Pilot> findPilotsWithFinishedFlightGroupedByGroup(Integer round, String group);

}
