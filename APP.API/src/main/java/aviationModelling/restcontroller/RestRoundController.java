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
    @GetMapping("/{roundNum}/{eventId}")
    public RoundDTO getRound(@PathVariable Integer roundNum, @PathVariable Integer eventId) {
        return roundService.findByRoundNumAndEventId(roundNum,eventId);
    }

    @ApiOperation(value = "Return all rounds")
    @JsonView(Views.Public.class)
    @GetMapping("/{eventId}/list")
    public List<RoundDTO> getRounds(@PathVariable Integer eventId) {
        return roundService.findAll(eventId);
    }

    @ApiOperation(value = "Return all rounds with list of flights")
    @JsonView(Views.Internal.class)
    @GetMapping("/{eventId}/list/with-flights")
    public List<RoundDTO> getRoundsWithFlights(@PathVariable Integer eventId) {
        return roundService.findAll(eventId);
    }

    @ApiOperation(value = "Return list of flights in the given round")
    @GetMapping("/{eventId}/flights/{roundNum}")
    public List<FlightDTO> getRoundFlights(@PathVariable Integer roundNum, @PathVariable Integer eventId) {
        return roundService.findRoundFlights(roundNum,eventId);
    }

    @ApiOperation(value = "Update all rounds")
    @PutMapping("/update/{eventId}")
    public ResponseEntity<CustomResponse> updateEveryRound(@PathVariable Integer eventId) {
        return roundService.updateAllRounds(eventId);
    }

    @ApiOperation(value = "Update local scores")
    @PutMapping("/update/{roundNum}/{eventId}")
    public ResponseEntity<CustomResponse> updateLocalScore(@PathVariable Integer roundNum,@PathVariable Integer eventId) {
        return roundService.updateLocalScore(roundNum,eventId);
    }

    @ApiOperation(value = "Create new round")
    @PostMapping("/new/{eventId}/{roundNum}")
    public ResponseEntity<RoundDTO> createRound(@PathVariable Integer roundNum, @PathVariable Integer eventId) {
        return roundService.createRound(roundNum, eventId);
    }

    @ApiOperation(value = "Cancel given round")
    @PutMapping("/cancel/{roundNum}/{eventId}")
    public ResponseEntity<CustomResponse> cancelRound(@PathVariable Integer roundNum, @PathVariable Integer eventId) {
        return roundService.cancelRound(roundNum,eventId);
    }

    @ApiOperation(value = "Uncancel given round")
    @PutMapping("/uncancel/{roundNum}/{eventId}")
    public ResponseEntity<CustomResponse> uncancelRound(@PathVariable Integer roundNum, @PathVariable Integer eventId) {
        return roundService.uncancelRound(roundNum,eventId);
    }

    @ApiOperation(value = "Finish given round")
    @PutMapping("/finish/{roundNum}/{eventId}")
    public ResponseEntity<CustomResponse>  finishRound(@PathVariable Integer roundNum, @PathVariable Integer eventId) {
        return roundService.finishRound(roundNum,eventId);
    }

    @ApiOperation(value = "Return round numbers")
    @GetMapping("/{eventId}/numbers")
    List<Integer> getRoundNumbers(@PathVariable Integer eventId) {
        return roundService.getRoundNumbers(eventId);
    }


}
