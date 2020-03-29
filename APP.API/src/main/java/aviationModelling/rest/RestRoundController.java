package aviationModelling.rest;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.RoundDTO;
import aviationModelling.entity.Flight;
import aviationModelling.entity.Round;
import aviationModelling.mapper.FlightMapper;
import aviationModelling.mapper.RoundMapper;
import aviationModelling.service.RoundService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rounds")
public class RestRoundController {

    private RoundService roundService;

    public RestRoundController(RoundService roundService) {
        this.roundService = roundService;
    }

//    zwroc runde o danym numerze
    @GetMapping("/{roundNum}")
    public RoundDTO getRound(@PathVariable Integer roundNum) {
        return RoundMapper.MAPPER.toRoundDTO(roundService.findByRoundNum(roundNum));
    }

//    uaktualnij wyniki po przelocie
    @PutMapping("/update/{roundNum}")
    public ResponseEntity<String> updateLocalScore(@PathVariable Integer roundNum) {
        return roundService.updateLocalScore(roundNum);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateEveryRound() {
        return roundService.updateAllRounds();
    }

//    stworz nowa runde
    @PostMapping("/events/{eventId}/new/{roundNum}")
    public ResponseEntity<String> createRound(@PathVariable Integer roundNum, @PathVariable Integer eventId) {
        return roundService.createRound(roundNum, eventId);
    }

//    zwroc liste przelotow w danej rundzie
    @GetMapping("/all-flights/{roundNum}")
    public List<FlightDTO> getRoundFlights(@PathVariable Integer roundNum) {
        return FlightMapper.MAPPER.toFlightDTOList(roundService.findRoundFlights(roundNum));
    }

//    zwroc liste nieanulowanych przelotow w danej rundzie
    @GetMapping("/uncancelled-flights/{roundNum}")
    public List<FlightDTO> getUncancelledRoundFlights(@PathVariable Integer roundNum) {
        return FlightMapper.MAPPER.toFlightDTOList(roundService.findUncancelledRoundFlights(roundNum));
    }

//    anuluj dana runde
    @PutMapping("/cancellations/{roundNum}")
    public ResponseEntity<String> cancelRound(@PathVariable Integer roundNum) {
        return roundService.cancelRound(roundNum);
    }

//    zakoncz dana runde i przelicz total score
    @PutMapping("/finish/{roundNum}")
    public ResponseEntity<String>  finishRound(@PathVariable Integer roundNum) {
        return roundService.finishRound(roundNum);
    }

    @GetMapping
    public List<RoundDTO> getRounds() {
        return RoundMapper.MAPPER.toRoundDTOList(roundService.findAll());
    }


}
