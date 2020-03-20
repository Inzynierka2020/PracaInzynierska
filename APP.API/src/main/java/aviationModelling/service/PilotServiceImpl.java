package aviationModelling.service;

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
        return pilotRepository.findAllByOrderByLastName();
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
    public List<Pilot> findPilotsWithFinishedFlight(Integer roundNum) {
        return pilotRepository.findPilotsWithFinishedFlight(roundNum);
    }

    @Override
    public List<Pilot> findPilotsWithUnfinishedFlight(Integer roundNum) {
        return pilotRepository.findPilotsWithUnfinishedFlight(roundNum);
    }

//    @Override
//    public List<Pilot> findPilotsByGroup(Integer round) {
//        return pilotRepository.findPilotsByGroup(round);
//    }

    @Override
    public List<Pilot> findPilotsWithFinishedFlightGroupedByGroup(Integer round, String group) {
        return pilotRepository.findPilotsWithFinishedFlightGroupedByGroup(round, group);
    }

    @Override
    public void savePilots(List<Pilot> pilots) {
        pilots.stream().forEach(pilot -> pilotRepository.save(pilot));
    }
}
