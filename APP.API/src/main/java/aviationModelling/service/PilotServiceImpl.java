package aviationModelling.service;

import aviationModelling.converter.Parser;
import aviationModelling.entity.Event;
import aviationModelling.entity.Pilot;
import aviationModelling.repository.PilotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PilotServiceImpl implements PilotService {

    private PilotRepository pilotRepository;

    public PilotServiceImpl(PilotRepository pilotRepository) {
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
    public Pilot findById(int id) {
        Optional<Pilot> result = pilotRepository.findById(id);
        Pilot pilot = null;

        if(result.isPresent()) {
            pilot=result.get();
        }
        return pilot;
    }

    @Override
    public void savePilots(List<Pilot> pilots) {
        pilots.stream().forEach(pilot -> pilotRepository.save(pilot));
    }
}
