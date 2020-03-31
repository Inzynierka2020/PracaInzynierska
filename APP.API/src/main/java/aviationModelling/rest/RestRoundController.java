package aviationModelling.rest;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.RoundDTO;
import aviationModelling.entity.Flight;
import aviationModelling.entity.Round;
import aviationModelling.exception.CustomResponse;
import aviationModelling.mapper.FlightMapper;
import aviationModelling.mapper.RoundMapper;
import aviationModelling.service.RoundService;
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

//    zwroc runde o danym numerze
    @GetMapping("/{roundNum}")
    public RoundDTO getRound(@PathVariable Integer roundNum) {
        return roundService.findByRoundNum(roundNum);
    }

//    uaktualnij wyniki po przelocie
    @PutMapping("/update/{roundNum}")
    public ResponseEntity<CustomResponse> updateLocalScore(@PathVariable Integer roundNum) {
        return roundService.updateLocalScore(roundNum);
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> updateEveryRound() {
        return roundService.updateAllRounds();
    }

//    stworz nowa runde
    @PostMapping("/events/{eventId}/new/{roundNum}")
    public ResponseEntity<RoundDTO> createRound(@PathVariable Integer roundNum, @PathVariable Integer eventId) {
        return roundService.createRound(roundNum, eventId);
    }

//    zwroc liste przelotow w danej rundzie
    @GetMapping("/all-flights/{roundNum}")
    public List<FlightDTO> getRoundFlights(@PathVariable Integer roundNum) {
        return roundService.findRoundFlights(roundNum);
    }

//    anuluj dana runde
    @PutMapping("/cancel/{roundNum}")
    public ResponseEntity<CustomResponse> cancelRound(@PathVariable Integer roundNum) {
        return roundService.cancelRound(roundNum);
    }

//    zakoncz dana runde i przelicz total score
    @PutMapping("/finish/{roundNum}")
    public ResponseEntity<CustomResponse>  finishRound(@PathVariable Integer roundNum) {
        return roundService.finishRound(roundNum);
    }

    @GetMapping
    public List<RoundDTO> getRounds() {
        return roundService.findAll();
    }

    @GetMapping("/numbers")
    List<Integer> getRoundNumbers() {
        return roundService.getRoundNumbers();
    }


}
