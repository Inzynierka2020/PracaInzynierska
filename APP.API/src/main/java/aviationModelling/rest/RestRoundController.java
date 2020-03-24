package aviationModelling.rest;

import aviationModelling.entity.Round;
import aviationModelling.service.RoundService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rounds")
public class RestRoundController {

    private RoundService roundService;

    public RestRoundController(RoundService roundService) {
        this.roundService = roundService;
    }

    @GetMapping("/{roundNum}")
    public Round getRound(@PathVariable Integer roundNum) {
        return roundService.findByRoundNum(roundNum);
    }

    @PostMapping("/create/{roundNum}")
    public ResponseEntity<String> createRound(@PathVariable Integer roundNum) {
        return roundService.createRound(roundNum);
    }

    @PutMapping("/cancel/{roundNum}")
    public ResponseEntity<String> cancelRound(@PathVariable Integer roundNum) {
        return roundService.cancelRound(roundNum);
    }

    @PutMapping("/finish/{roundNum}")
    public ResponseEntity<String> finishRound(@PathVariable Integer roundNum) {
        return roundService.updateGeneralScore(roundNum);
    }
}
