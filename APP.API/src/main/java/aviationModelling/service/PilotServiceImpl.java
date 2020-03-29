package aviationModelling.service;

import aviationModelling.entity.Flight;
import aviationModelling.entity.Pilot;
import aviationModelling.repository.PilotRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<Pilot> findAllOrderByScore() {
        return pilotRepository.findAllByOrderByScoreDesc();
    }

    @Override
    public ResponseEntity<String> save(Pilot pilot) {
        pilotRepository.save(pilot);
        return new ResponseEntity<>("Pilot save successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> saveAll(List<Pilot> pilotList) {
        pilotRepository.saveAll(pilotList);
        return new ResponseEntity<>("Pilot list saved successfully", HttpStatus.OK);
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

    @Override
    public List<Pilot> findPilotsWithFinishedFlightGroupedByGroup(Integer round, String group) {
        return pilotRepository.findPilotsWithFinishedFlightGroupedByGroup(round, group);
    }


    @Override
    public Float findBestPilotScore(Integer pilotId) {
        return pilotRepository.findBestPilotScore(pilotId);
    }

    @Override
    public Float findBestPilotTime(Integer pilotId) {
        return pilotRepository.findBestPilotTime(pilotId);
    }

    @Override
    public List<Flight> findUncancelledAndFinishedPilotFlights(Integer pilotId) {
        return pilotRepository.findUncancelledAndFinishedPilotFlights(pilotId);
    }

    @Override
    public List<Flight> findPilotFlights(Integer pilotId) {
        return pilotRepository.findPilotFlights(pilotId);
    }
}
