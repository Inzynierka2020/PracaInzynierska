package aviationModelling.service;

import aviationModelling.entity.Flight;
import aviationModelling.repository.FlightRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private FlightRepository flightRepository;
    private RoundService roundService;

    public FlightServiceImpl(FlightRepository flightRepository, RoundService roundService) {
        this.flightRepository = flightRepository;
        this.roundService = roundService;
    }

    @Override
    public ResponseEntity<String> save(Flight flight) {
        flightRepository.save(flight);
        return new ResponseEntity<>("Flight saved successfully!", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> saveAll(List<Flight> flightList) {
        flightRepository.saveAll(flightList);
        return new ResponseEntity<>("All flights saved successfully", HttpStatus.CREATED);
    }

    @Override
    public Flight findBestTime() {
        return flightRepository.findBestTime();
    }

    @Override
    public Flight findBestScore() {
        return flightRepository.findBestScore();
    }

    @Override
    public Flight findFlight(Integer roundNum, Integer pilotId) {
        return flightRepository.findByFlightIdRoundNumAndFlightIdPilotId(roundNum, pilotId);
    }
}
