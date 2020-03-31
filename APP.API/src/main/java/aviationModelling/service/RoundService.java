package aviationModelling.service;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.RoundDTO;
import aviationModelling.entity.Flight;
import aviationModelling.entity.Round;
import aviationModelling.exception.CustomResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoundService {

    ResponseEntity<RoundDTO> save(RoundDTO roundDTO);

    RoundDTO findByRoundNum(Integer roundNum);

    ResponseEntity<RoundDTO> createRound(Integer roundNum, Integer eventId);

    ResponseEntity<CustomResponse> updateLocalScore(Integer roundNum);

    ResponseEntity<CustomResponse> cancelRound(Integer roundNum);

    List<FlightDTO> findRoundFlights(Integer roundNum);


    ResponseEntity<CustomResponse> finishRound(Integer roundNum);

    List<Integer> getRoundNumbers();

    ResponseEntity<CustomResponse> updateAllRounds();

    List<RoundDTO> findAll();
}
