package aviationModelling.rest;

import aviationModelling.entity.Flight;
import aviationModelling.entity.Pilot;
import aviationModelling.service.PilotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pilots")
public class RestPilotController {

    private PilotService pilotService;


    public RestPilotController(PilotService pilotService) {
        this.pilotService = pilotService;
    }

//    zwroc wszystkich pilotow (kolejnosc alfabetyczna)
    @GetMapping
    public List<Pilot> getPilots() {
        return pilotService.findAll();
    }

//    zwroc danego pilota
    @GetMapping("/{pilotId}")
    public Pilot getPilotById(@PathVariable int pilotId) {
        return pilotService.findById(pilotId);
    }

//    zwroc wszystkie loty pilota o danym id
    @GetMapping("/pilot-rounds/{pilotId}")
    public List<Flight> pilotRounds(@PathVariable int pilotId) {
        return pilotService.findPilotFlights(pilotId);
    }

//    uaktualnij pilota
    @PutMapping
    public ResponseEntity<String> updatePilot(@RequestBody Pilot pilot) {
        return pilotService.save(pilot);
    }

//    zwroc liste pilotow, ktorzy odbyli swoj lot w danej rundzie (sortuj wg wyniku malejaco, potem wg nazwiska)
    @GetMapping("finished-flight/{roundNum}")
    public List<Pilot> getPilotsWithFinishedFlight(@PathVariable Integer roundNum) {
        return pilotService.findPilotsWithFinishedFlight(roundNum);
    }

//    zwroc liste pilotow, ktorzy nie odbyli swojego lotu w danej rundzie (sortuj wg nazwiska)
    @GetMapping("unfinished-flight/{roundNum}")
    public List<Pilot> getPilotsWithUnfinishedFlight(@PathVariable Integer roundNum) {
        return pilotService.findPilotsWithUnfinishedFlight(roundNum);
    }

//    zwroc liste pilotow, ktorzy odbyli swoj lot w danej rundzie i naleza do danej grupy (sortuj wg wyniku desc, potem nazwiska)
    @GetMapping("/finished-flight-group-by-group/round={roundNum}&group={group}")
    public List<Pilot> getPilotsFromGroup(@PathVariable Integer roundNum, @PathVariable String group) {
        return pilotService.findPilotsWithFinishedFlightGroupedByGroup(roundNum,group);
    }

//    zwroc liste odrzuconych wynikow danego pilota (jeden albo dwa)
    @GetMapping("/discarded-flights/{pilotId}")
    public List<Float> getDiscardedFlights(@PathVariable Integer pilotId) {
        return pilotService.findDiscardedFlights(pilotId);
    }

//    zwroc najlepszy wynik danego pilota
    @GetMapping("/best-score/{pilotId}")
    public Float getBestScore(@PathVariable Integer pilotId) {
        return pilotService.findBestScore(pilotId);
    }

//    zwroc najlepszy czas danego pilota
    @GetMapping("/best-time/{pilotId}")
    public Float getBestTime(@PathVariable Integer pilotId) {
        return pilotService.findBestTime(pilotId);
    }

}
