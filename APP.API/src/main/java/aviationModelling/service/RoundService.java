package aviationModelling.service;

import aviationModelling.dto.EventRoundDTO;
import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.RoundDTO;
import aviationModelling.entity.Flight;
import aviationModelling.entity.Round;
import aviationModelling.exception.CustomResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoundService {

//    ResponseEntity<RoundDTO> save(RoundDTO roundDTO);
//
//    EventRoundDTO findByRoundNumAndEventId(Integer roundNum, Integer eventId);
//
//    ResponseEntity<RoundDTO> createRound(Integer roundNum, Integer eventId);
//
//    ResponseEntity<CustomResponse> updateLocalScore(Integer roundNum, Integer eventId);
//
//    ResponseEntity<CustomResponse> cancelRound(Integer roundNum, Integer eventId);
//
//    ResponseEntity<CustomResponse> uncancelRound(Integer roundNum, Integer eventId);
//
//    List<FlightDTO> findRoundFlights(Integer roundNum, Integer eventId);
//
//    ResponseEntity<CustomResponse> finishRound(Integer roundNum, Integer eventId);
//
//    List<Integer> getRoundNumbers(Integer eventId);
//
//    ResponseEntity<CustomResponse> updateAllRounds(Integer eventId);
//
//    List<EventRoundDTO> findAll(Integer eventId);
}
