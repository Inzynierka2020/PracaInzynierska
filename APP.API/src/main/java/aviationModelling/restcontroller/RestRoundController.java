package aviationModelling.restcontroller;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.RoundDTO;
import aviationModelling.dto.Views;
import aviationModelling.exception.CustomResponse;
import aviationModelling.service.RoundService;
import com.fasterxml.jackson.annotation.JsonView;
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

    @ApiOperation(value = "Return all rounds")
    @JsonView(Views.Public.class)
    @GetMapping("/list")
    public List<RoundDTO> getRounds() {
        return roundService.findAll();
    }

    @ApiOperation(value = "Return all rounds with list of flights")
    @JsonView(Views.Internal.class)
    @GetMapping("/list/with-flights")
    public List<RoundDTO> getRoundsWithFlights() {
        return roundService.findAll();
    }

    @ApiOperation(value = "Return list of flights in the given round")
    @GetMapping("/flights/{roundNum}")
    public List<FlightDTO> getRoundFlights(@PathVariable Integer roundNum) {
        return roundService.findRoundFlights(roundNum);
    }

    @ApiOperation(value = "Update round")
    @PutMapping("/update")
    public ResponseEntity<CustomResponse> updateEveryRound() {
        return roundService.updateAllRounds();
    }

    @ApiOperation(value = "Update local scores")
    @PutMapping("/update/{roundNum}")
    public ResponseEntity<CustomResponse> updateLocalScore(@PathVariable Integer roundNum) {
        return roundService.updateLocalScore(roundNum);
    }

    @ApiOperation(value = "Create new round")
    @PostMapping("/new/{eventId}/{roundNum}")
    public ResponseEntity<RoundDTO> createRound(@PathVariable Integer roundNum, @PathVariable Integer eventId) {
        return roundService.createRound(roundNum, eventId);
    }

    @ApiOperation(value = "Cancel given round")
    @PutMapping("/cancel/{roundNum}")
    public ResponseEntity<CustomResponse> cancelRound(@PathVariable Integer roundNum) {
        return roundService.cancelRound(roundNum);
    }

    @ApiOperation(value = "Uncancel given round")
    @PutMapping("/uncancel/{roundNum}")
    public ResponseEntity<CustomResponse> uncancelRound(@PathVariable Integer roundNum) {
        return roundService.uncancelRound(roundNum);
    }

    @ApiOperation(value = "Finish given round")
    @PutMapping("/finish/{roundNum}")
    public ResponseEntity<CustomResponse>  finishRound(@PathVariable Integer roundNum) {
        return roundService.finishRound(roundNum);
    }

    @ApiOperation(value = "Return round numbers")
    @GetMapping("/numbers")
    List<Integer> getRoundNumbers() {
        return roundService.getRoundNumbers();
    }


}
