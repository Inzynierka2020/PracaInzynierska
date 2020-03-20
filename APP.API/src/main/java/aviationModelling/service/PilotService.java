package aviationModelling.service;

import aviationModelling.entity.Pilot;

import java.util.List;

public interface PilotService {

    List<Pilot> findAll();
    void savePilots(List<Pilot> pilots);
    void save(Pilot pilot);
    Pilot findById(int id);
    List<Pilot> findPilotsWithFinishedFlight(Integer roundNum);
    List<Pilot> findPilotsWithUnfinishedFlight(Integer roundNum);
//    List<Pilot> findPilotsByGroup(Integer round);
    List<Pilot> findPilotsWithFinishedFlightGroupedByGroup(Integer round, String group);

}
