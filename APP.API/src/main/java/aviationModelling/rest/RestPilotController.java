package aviationModelling.rest;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.PilotDTO;
import aviationModelling.entity.Flight;
import aviationModelling.entity.Pilot;
import aviationModelling.mapper.FlightMapper;
import aviationModelling.mapper.PilotMapper;
import aviationModelling.service.PilotService;
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
        return PilotMapper.MAPPER.toPilotListDTO(pilotService.findAll());
    }

    //    zwroc wszystkich pilotow (sortuj po wyniku)
    @GetMapping("/ranking")
    public List<PilotDTO> getPilotRanking() {
        return PilotMapper.MAPPER.toPilotListDTO(pilotService.findAllOrderByScore());
    }

    //    uaktualnij pilota
    @PutMapping
    public ResponseEntity<String> updatePilot(@RequestBody PilotDTO pilotDTO) {
        return pilotService.save(pilotDTO);
    }

//    zwroc danego pilota
    @GetMapping("/{pilotId}")
    public PilotDTO getPilotById(@PathVariable int pilotId) {
        return PilotMapper.MAPPER.toPilotDTO(pilotService.findById(pilotId));
    }

//    zwroc wszystkie loty pilota o danym id
    @GetMapping("/{pilotId}/flights")
    public List<FlightDTO> pilotFlights(@PathVariable int pilotId) {
        return FlightMapper.MAPPER.toFlightDTOList(pilotService.findPilotFlights(pilotId));
    }

//    zwroc wszystkie loty pilota o danym id
    @GetMapping("/{pilotId}/finished-flights")
    public List<FlightDTO> pilotFinishedFlights(@PathVariable int pilotId) {
        return FlightMapper.MAPPER.toFlightDTOList(pilotService.findUncancelledAndFinishedPilotFlights(pilotId));
    }

//    zwroc liste pilotow, ktorzy odbyli swoj lot w danej rundzie (sortuj wg wyniku malejaco, potem wg nazwiska)
    @GetMapping("/rounds/{roundNum}/finished-flights")
    public List<PilotDTO> getPilotsWithFinishedFlight(@PathVariable Integer roundNum) {
        return PilotMapper.MAPPER.toPilotListDTO(pilotService.findPilotsWithFinishedFlight(roundNum));
    }

//    zwroc liste pilotow, ktorzy nie odbyli swojego lotu w danej rundzie (sortuj wg nazwiska)
    @GetMapping("/rounds/{roundNum}/unfinished-flights")
    public List<PilotDTO> getPilotsWithUnfinishedFlight(@PathVariable Integer roundNum) {
        return PilotMapper.MAPPER.toPilotListDTO(pilotService.findPilotsWithUnfinishedFlight(roundNum));
    }

//    zwroc liste pilotow, ktorzy odbyli swoj lot w danej rundzie i naleza do danej grupy (sortuj wg wyniku desc, potem nazwiska)
    @GetMapping("/rounds/{roundNum}/finished-flights/{group}")
    public List<PilotDTO> getPilotsFromGroup(@PathVariable Integer roundNum, @PathVariable String group) {
        return PilotMapper.MAPPER.toPilotListDTO(pilotService.findPilotsWithFinishedFlightGroupedByGroup(roundNum,group));
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
