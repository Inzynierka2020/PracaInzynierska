package aviationModelling.rest;

import aviationModelling.entity.Round;
import aviationModelling.service.RoundService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rounds")
public class RestRoundController {

    private RoundService roundService;

    public RestRoundController(RoundService roundService) {
        this.roundService = roundService;
    }

    @GetMapping("/event-round/{round}")
    public List<Round> getSpecificRound(@PathVariable int round) {
        List<Round> roundList = roundService.findByRoundIdRoundNum(round);
        return roundList;
    }

    @GetMapping("/pilot-rounds/{pilot_id}")
    public List<Round> getPilotRounds(@PathVariable int pilot_id) {
        List<Round> roundList = roundService.findByRoundIdPilotId(pilot_id);
        return roundList;
    }

}
