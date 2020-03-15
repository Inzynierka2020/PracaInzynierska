package aviationModellingApp.service;

import aviationModellingApp.converter.Parser;
import aviationModellingApp.entity.Pilot;
import aviationModellingApp.repository.PilotRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PilotServiceImpl implements PilotService {

    private PilotRepository pilotRepository;

    public PilotServiceImpl(PilotRepository pilotRepository,
                            Parser stringToPilotListParser) {
        this.pilotRepository = pilotRepository;
    }

    @Override
    public List<Pilot> findAll() {
        return pilotRepository.findAll();
    }

    @Override
    public void save(Pilot pilot) {
        pilotRepository.save(pilot);
    }

    @Override
    public void savePilots(List<Pilot> pilots) {
        pilots.stream().forEach(pilot -> pilotRepository.save(pilot));
    }

}
