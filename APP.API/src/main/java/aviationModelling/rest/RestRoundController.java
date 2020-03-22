package aviationModelling.rest;

import aviationModelling.entity.Round;
import aviationModelling.service.RoundService;
import org.springframework.http.HttpStatus;
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

//    wyswietl wszystkie loty w danej rundzie
    @GetMapping("/event-round/{round}")
    public List<Round> getSpecificRound(@PathVariable int round) {
        List<Round> roundList = roundService.findByRoundNum(round);
        return roundList;
    }

//      wyswietl wszystkie rundy danego pilota
    @GetMapping("/pilot-rounds/{pilot_id}")
    public List<Round> getPilotRounds(@PathVariable int pilot_id) {
        List<Round> roundList = roundService.findByPilotId(pilot_id);
        return roundList;
    }

    @PostMapping
    public ResponseEntity<String> saveRound(@RequestBody Round round) {
        return roundService.save(round);
    }

    @PutMapping
    public ResponseEntity<String> updateRound(@RequestBody Round round) {
        return roundService.save(round);
    }

    @PutMapping("/finish/{roundId}")
    public ResponseEntity<String> finishRound(@PathVariable Integer roundId) {
        return roundService.recalculateTotalScores(roundId);
    }

    @GetMapping("/best/{pilotId}")
    public Round getBest(@PathVariable Integer pilotId) {
        return roundService.getBest(pilotId);
    }




}
