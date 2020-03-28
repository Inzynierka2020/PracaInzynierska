package aviationModelling.service;

import aviationModelling.entity.Flight;
import aviationModelling.entity.Pilot;
import aviationModelling.entity.Round;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PilotService {

    List<Pilot> findAll();
    ResponseEntity<String> save(Pilot pilot);
    ResponseEntity<String> saveAll(List<Pilot> pilotList);

    Pilot findById(int id);
    List<Pilot> findPilotsWithFinishedFlight(Integer roundNum);
    List<Pilot> findPilotsWithUnfinishedFlight(Integer roundNum);
    List<Pilot> findPilotsWithFinishedFlightGroupedByGroup(Integer round, String group);

    Float findBestPilotScore(Integer pilotId);
    Float findBestPilotTime(Integer pilotId);

    List<Flight> findPilotFlights(Integer pilotId);
    List<Flight> findUncancelledAndFinishedPilotFlights(Integer pilotId);

}
