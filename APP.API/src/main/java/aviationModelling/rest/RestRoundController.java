package aviationModelling.rest;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.RoundDTO;
import aviationModelling.entity.Flight;
import aviationModelling.entity.Round;
import aviationModelling.exception.CustomResponse;
import aviationModelling.mapper.FlightMapper;
import aviationModelling.mapper.RoundMapper;
import aviationModelling.service.RoundService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rounds")
@CrossOrigin(origins = "*")
public class RestRoundController {

    private RoundService roundService;

    public RestRoundController(RoundService roundService) {
        this.roundService = roundService;
    }

    @ApiOperation(value = "Return round with the given id")
    @GetMapping("/{roundNum}")
    public RoundDTO getRound(@PathVariable Integer roundNum) {
        return roundService.findByRoundNum(roundNum);
    }

    @ApiOperation(value = "Update local scores")
    @PutMapping("/update/{roundNum}")
    public ResponseEntity<CustomResponse> updateLocalScore(@PathVariable Integer roundNum) {
        return roundService.updateLocalScore(roundNum);
    }

    @ApiOperation(value = "Update round")
    @PutMapping("/update")
    public ResponseEntity<CustomResponse> updateEveryRound() {
        return roundService.updateAllRounds();
    }

    @ApiOperation(value = "Create new round")
    @PostMapping("/new/{eventId}/{roundNum}")
    public ResponseEntity<RoundDTO> createRound(@PathVariable Integer roundNum, @PathVariable Integer eventId) {
        return roundService.createRound(roundNum, eventId);
    }

    @ApiOperation(value = "Return list of flights in the given round")
    @GetMapping("/all-flights/{roundNum}")
    public List<FlightDTO> getRoundFlights(@PathVariable Integer roundNum) {
        return roundService.findRoundFlights(roundNum);
    }

    @ApiOperation(value = "Cancel given round")
    @PutMapping("/cancel/{roundNum}")
    public ResponseEntity<CustomResponse> cancelRound(@PathVariable Integer roundNum) {
        return roundService.cancelRound(roundNum);
    }

    @ApiOperation(value = "Finish given round")
    @PutMapping("/finish/{roundNum}")
    public ResponseEntity<CustomResponse>  finishRound(@PathVariable Integer roundNum) {
        return roundService.finishRound(roundNum);
    }

    @ApiOperation(value = "Return all rounds")
    @GetMapping
    public List<RoundDTO> getRounds() {
        return roundService.findAll();
    }

    @ApiOperation(value = "Return round numbers")
    @GetMapping("/numbers")
    List<Integer> getRoundNumbers() {
        return roundService.getRoundNumbers();
    }


}
