package aviationModelling.restcontroller;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.RoundDTO;
import aviationModelling.dto.VaultResponseDTO;
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

    @ApiOperation(value = "Return all rounds")
    @JsonView(Views.Public.class)
    @GetMapping
    public List<RoundDTO> getRounds(@RequestParam Integer eventId) {
        return roundService.findAll(eventId);
    }

    @ApiOperation(value = "Return round with the given id")
    @GetMapping("/{roundNum}")
    public RoundDTO getRound(@PathVariable Integer roundNum, @RequestParam Integer eventId) {
        return roundService.findEventRound(roundNum,eventId);
    }

    @ApiOperation(value = "Return list of flights in the given round")
    @GetMapping("/{roundNum}/flights")
    public List<FlightDTO> getRoundFlights(@PathVariable Integer roundNum, @RequestParam Integer eventId) {
        return roundService.getRoundFlights(roundNum, eventId);
    }

//
//    @ApiOperation(value = "Return all rounds with list of flights")
//    @JsonView(Views.Internal.class)
//    @GetMapping("with-flights")
//    public List<RoundDTO> getRoundsWithFlights(@RequestParam Integer eventId) {
//        return roundService.findAll(eventId);
//    }
//


    @ApiOperation(value = "Update all rounds")
    @PutMapping("/update")
    public ResponseEntity<CustomResponse> updateEveryRound(@RequestParam Integer eventId) {
        return roundService.updateAllRounds(eventId);
    }

    @ApiOperation(value = "Update local scores")
    @PutMapping("/update/{roundNum}")
    public ResponseEntity<CustomResponse> updateLocalScore(@PathVariable Integer roundNum,@RequestParam Integer eventId) {
        return roundService.updateLocalScore(roundNum,eventId);
    }

    @ApiOperation(value = "Create new round")
    @PostMapping("/new/{roundNum}")
    public ResponseEntity<RoundDTO> createRound(@PathVariable Integer roundNum, @RequestParam Integer eventId) {
        return roundService.createRound(roundNum, eventId);
    }

    @ApiOperation(value = "Cancel given round")
    @PutMapping("/cancel/{roundNum}")
    public ResponseEntity<CustomResponse> cancelRound(@PathVariable Integer roundNum, @RequestParam Integer eventId) {
        return roundService.cancelRound(roundNum,eventId);
    }

    @ApiOperation(value = "Uncancel given round")
    @PutMapping("/uncancel/{roundNum}")
    public ResponseEntity<CustomResponse> uncancelRound(@PathVariable Integer roundNum, @RequestParam Integer eventId) {
        return roundService.uncancelRound(roundNum,eventId);
    }

    @ApiOperation(value = "Finish given round")
    @PutMapping("/finish/{roundNum}")
    public ResponseEntity<CustomResponse>  finishRound(@PathVariable Integer roundNum, @RequestParam Integer eventId) {
        return roundService.finishRound(roundNum,eventId);
    }

    @ApiOperation(value = "Return round numbers")
    @GetMapping("/numbers")
    public List<Integer> getRoundNumbers(@RequestParam Integer eventId) {
        return roundService.getRoundNumbers(eventId);
    }

    @ApiOperation(value = "Return the best round flight")
    @GetMapping("/best/{roundNum}")
    public FlightDTO getRoundNumbers(@PathVariable Integer roundNum, @RequestParam Integer eventId) {
        return roundService.findBestRoundFlight(roundNum, eventId);
    }

    @ApiOperation(value = "Update event round status on F3XVault")
    @PostMapping("/vault-update")
    public ResponseEntity<VaultResponseDTO> createRound(@RequestBody RoundDTO roundDTO) {
        return roundService.updateEventRoundStatus(roundDTO);
    }
}
