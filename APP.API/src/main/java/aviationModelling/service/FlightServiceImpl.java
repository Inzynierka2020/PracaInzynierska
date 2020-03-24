package aviationModelling.service;

import aviationModelling.entity.Flight;
import aviationModelling.entity.Pilot;
import aviationModelling.repository.FlightRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlightServiceImpl implements FlightService {

    private FlightRepository flightRepository;
    private PilotService pilotService;
    private RoundService roundService;

    public FlightServiceImpl(FlightRepository flightRepository, PilotService pilotService, RoundService roundService) {
        this.flightRepository = flightRepository;
        this.pilotService = pilotService;
        this.roundService = roundService;
    }

    @Override
    public List<Flight> findByPilotId(int pilotId) {
        return flightRepository.findByFlightIdPilotIdOrderByFlightIdRoundNum(pilotId);
    }

    @Override
    public List<Flight> findByRoundNum(int roundNum) {
        return flightRepository.findByFlightIdRoundNumOrderBySeconds(roundNum);
    }

    @Override
    public ResponseEntity<String> saveAll(List<Flight> flightList) {
        flightRepository.saveAll(flightList);
        return new ResponseEntity<>("Flight list saved successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> save(Flight flight) {
        flightRepository.save(flight);
        return roundService.updateScore(flight.getFlightId().getRoundNum());
    }

    @Override
    public Float getBestScore(Integer pilotId) {
        return flightRepository.findTopByFlightIdPilotIdOrderByScoreDesc(pilotId).getScore();
    }

    @Override
    public Float getBestTime(Integer pilotId) {
        return flightRepository.findTopByFlightIdPilotIdOrderBySecondsAsc(pilotId).getSeconds();
    }


}
