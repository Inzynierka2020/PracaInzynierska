package aviationModelling.rest;

import aviationModelling.entity.Flight;
import aviationModelling.entity.Round;
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
    public Round getRound(@PathVariable Integer roundNum) {
        return roundService.findByRoundNum(roundNum);
    }

//    zwroc liste przelotow w danej rundzie
    @GetMapping("/round-flights/{roundNum}")
    public List<Flight> getRoundFlights(@PathVariable Integer roundNum) {
        return roundService.findRoundFlights(roundNum);
    }

//    zwroc liste nieanulowanych przelotow w danej rundzie
    @GetMapping("/round-uncancelled-flights/{roundNum}")
    public List<Flight> getUncancelledRoundFlights(@PathVariable Integer roundNum) {
        return roundService.findUncancelledRoundFlights(roundNum);
    }

//    stworz nowa runde
    @PostMapping("/create/{roundNum}")
    public ResponseEntity<String> createRound(@PathVariable Integer roundNum) {
        return roundService.createRound(roundNum);
    }

//    anuluj dana runde
    @PutMapping("/cancel/{roundNum}")
    public ResponseEntity<String> cancelRound(@PathVariable Integer roundNum) {
        return roundService.cancelRound(roundNum);
    }

    @PutMapping("/update-local-score/{roundNum}")
    public ResponseEntity<String> updateLocalScore(@PathVariable Integer roundNum) {
        return roundService.updateLocalScore(roundNum);
    }

//    zakoncz dana runde i przelicz total score
    @PutMapping("/finish/{roundNum}")
    public ResponseEntity<String>  finishRound(@PathVariable Integer roundNum) {
        return roundService.finishRound(roundNum);
    }


}
