package aviationModellingApp.service;

import aviationModellingApp.entity.Pilot;

import java.io.IOException;
import java.util.List;

public interface PilotService {

    List<Pilot> findAll();
    void savePilots(List<Pilot> pilots);
    void save(Pilot pilot);

}
