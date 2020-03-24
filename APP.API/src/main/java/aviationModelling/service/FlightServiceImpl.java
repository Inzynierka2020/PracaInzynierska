package aviationModelling.service;

import aviationModelling.entity.Flight;
import aviationModelling.repository.FlightRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        return roundService.updateScore(flight.getFlightId().getRoundNum());
    }


}
