package aviationModelling.service;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.PilotDTO;
import aviationModelling.entity.Flight;
import aviationModelling.entity.Pilot;
import aviationModelling.entity.Round;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PilotService {

    List<PilotDTO> findAll();
    List<PilotDTO> findAllOrderByScore();
    ResponseEntity<PilotDTO> save(PilotDTO pilotDTO);
    ResponseEntity<String> saveAll(List<Pilot> pilotList);

    PilotDTO findById(int id);
    List<FlightDTO> findPilotFlights(Integer pilotId);
    List<PilotDTO> findPilotsWithFinishedFlight(Integer roundNum);
    List<PilotDTO> findPilotsWithUnfinishedFlight(Integer roundNum);
    List<PilotDTO> findPilotsWithFinishedFlightGroupedByGroup(Integer round, String group);

    Float findBestPilotScore(Integer pilotId);
    Float findBestPilotTime(Integer pilotId);

    List<FlightDTO> findUncancelledAndFinishedPilotFlights(Integer pilotId);

}
