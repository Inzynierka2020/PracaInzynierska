package aviationModelling.service;

import aviationModelling.entity.Pilot;

import java.util.List;

public interface PilotService {

    List<Pilot> findAll();
    void savePilots(List<Pilot> pilots);
    void save(Pilot pilot);
    Pilot findById(int id);
}
