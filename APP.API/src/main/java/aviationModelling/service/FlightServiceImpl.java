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
    public ResponseEntity<String> recalculateTotalScores(Integer roundNum) {
        List<Pilot> pilotList = pilotService.findAll();
        if (!roundNum.equals(4) && !roundNum.equals(15)) {
            pilotList = recalculateNormal(roundNum, pilotList);
        } else {
            pilotList = recalculateAfter4Or15Round(pilotList);
        }
        pilotService.saveAll(pilotList);
        return new ResponseEntity<>("Scores recalculated successfully", HttpStatus.OK);
    }

    private List<Pilot> recalculateAfter4Or15Round(List<Pilot> pilotList) {

        for (Pilot pilot : pilotList) {
            List<Flight> pilotsFlights = findByPilotId(pilot.getId());
            if (pilotsFlights.size() == 0) {
                continue;
            }
//            wybierz najgorszy wynik sposrod nieodrzuconych
            pilotsFlights.stream()
                    .filter(flight -> !flight.isDiscarded() && !flight.isCancelled())
                    .min(Comparator.comparingDouble(Flight::getScore))
                    .get()
                    .setDiscarded(true);
//
            Float totalScore = 0F;
            for (Flight flight : pilotsFlights) {
                if (flight.isDiscarded() == false) {
                    totalScore += flight.getScore();
                }
            }
            pilot.setScore(totalScore);
        }
        return pilotList;
    }

    private List<Pilot> recalculateNormal(Integer roundNum, List<Pilot> pilotList) {
        List<Flight> flightList = findByRoundNum(roundNum);
        Map<Integer, Float> results = new HashMap<>();
//        utwórz mapę z id_pilota i ich wynikami z ostatniej rundy
        flightList.stream()
                .filter(flight -> flight.isDiscarded() == false)  //wez nieodrzucone wyniki
                .forEach(flight -> results.put(flight.getFlightId().getPilotId(), flight.getScore() - flight.getPenalty()));
//        if (w results key == id_pilota) => pilot.setScore(wynik - kara)
        pilotList.stream()
                .filter(pilot -> results.containsKey(pilot.getId()))    //
                .forEach(pilot -> pilot.setScore(pilot.getScore() +
                        results.entrySet().stream()
                                .filter(result -> result.getKey().equals(pilot.getId()))
                                .findFirst().get().getValue()));
        return pilotList;
    }

    @Override
    public Flight getBest(Integer pilotId) {
        List<Flight> flights = findByPilotId(pilotId);
        if (flights.size() != 0) {
            return flights.stream().max(Comparator.comparingDouble(Flight::getScore)).get();
        } else return null;
    }


}
