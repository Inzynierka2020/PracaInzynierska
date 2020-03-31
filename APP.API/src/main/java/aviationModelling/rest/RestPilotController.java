package aviationModelling.rest;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.PilotDTO;
import aviationModelling.dto.Views;
import aviationModelling.service.PilotService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pilots")
@CrossOrigin(origins = "*")
public class RestPilotController {

    private PilotService pilotService;

    public RestPilotController(PilotService pilotService) {
        this.pilotService = pilotService;
    }

//    zwroc wszystkich pilotow (kolejnosc alfabetyczna)
    @GetMapping
    public List<PilotDTO> getPilots() {
        return pilotService.findAll();
    }

//    zwroc wszystkich pilotow (sortuj po wyniku)
    @GetMapping("/ranking")
    public List<PilotDTO> getPilotRanking() {
        return pilotService.findAllOrderByScore();
    }

//    uaktualnij pilota
    @PutMapping
    public ResponseEntity<PilotDTO> updatePilot(@RequestBody PilotDTO pilotDTO) {
        return pilotService.save(pilotDTO);
    }

//    zwroc danego pilota
    @JsonView(Views.Internal.class)
    @GetMapping("/{pilotId}")
    public PilotDTO getPilotById(@PathVariable int pilotId) {
        return pilotService.findById(pilotId);
    }

//    zwroc wszystkie loty pilota o danym id
    @GetMapping("/{pilotId}/flights")
    public List<FlightDTO> pilotFlights(@PathVariable int pilotId) {
        return pilotService.findPilotFlights(pilotId);
    }

//    zwroc ukonczone wszystkie loty pilota o danym id
    @GetMapping("/{pilotId}/finished-flights")
    public List<FlightDTO> pilotFinishedFlights(@PathVariable int pilotId) {
        return pilotService.findUncancelledAndFinishedPilotFlights(pilotId);
    }

//    zwroc liste pilotow, ktorzy odbyli swoj lot w danej rundzie (sortuj wg wyniku malejaco, potem wg nazwiska)
    @GetMapping("/rounds/{roundNum}/finished-flights")
    public List<PilotDTO> getPilotsWithFinishedFlight(@PathVariable Integer roundNum) {
        return pilotService.findPilotsWithFinishedFlight(roundNum);
    }

//    zwroc liste pilotow, ktorzy nie odbyli swojego lotu w danej rundzie (sortuj wg nazwiska)
    @GetMapping("/rounds/{roundNum}/unfinished-flights")
    public List<PilotDTO> getPilotsWithUnfinishedFlight(@PathVariable Integer roundNum) {
        return pilotService.findPilotsWithUnfinishedFlight(roundNum);
    }

//    zwroc liste pilotow, ktorzy odbyli swoj lot w danej rundzie i naleza do danej grupy (sortuj wg wyniku desc, potem nazwiska)
    @GetMapping("/rounds/{roundNum}/finished-flights/{group}")
    public List<PilotDTO> getPilotsFromGroup(@PathVariable Integer roundNum, @PathVariable String group) {
        return pilotService.findPilotsWithFinishedFlightGroupedByGroup(roundNum,group);
    }

//    zwroc najlepszy wynik danego pilota
    @GetMapping("/best-score/{pilotId}")
    public Float getBestScore(@PathVariable Integer pilotId) {
        return pilotService.findBestPilotScore(pilotId);
    }

//    zwroc najlepszy czas danego pilota
    @GetMapping("/best-time/{pilotId}")
    public Float getBestTime(@PathVariable Integer pilotId) {
        return pilotService.findBestPilotTime(pilotId);
    }



}
