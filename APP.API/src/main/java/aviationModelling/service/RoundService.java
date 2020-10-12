package aviationModelling.service;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.RoundDTO;
import aviationModelling.dto.VaultResponseDTO;
import aviationModelling.entity.EventRound;
import aviationModelling.exception.CustomResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoundService {

    List<RoundDTO> findAll(Integer eventId);

//    EventRound findEventRound(Integer roundNum, Integer eventId);

    //    ResponseEntity<RoundDTO> save(RoundDTO roundDTO);
    EventRound findEventRound(Integer roundNum, Integer eventId);
    ResponseEntity<RoundDTO> createRound(Integer roundNum, Integer eventId, Integer numberOfGroups);
//    ResponseEntity<RoundDTO> createRound(RoundDTO roundDTO);
    ResponseEntity<CustomResponse> cancelRound(Integer roundNum, Integer eventId);
    ResponseEntity<CustomResponse> uncancelRound(Integer roundNum, Integer eventId);
    ResponseEntity<CustomResponse> finishRound(Integer roundNum, Integer eventId);
    ResponseEntity<CustomResponse> updateLocalScore(Integer roundNum, Integer eventId);
    List<FlightDTO> getRoundFlights(Integer roundNum, Integer eventId);
    List<Integer> getRoundNumbers(Integer eventId);
    ResponseEntity<CustomResponse> updateAllRounds(Integer eventId);
    FlightDTO findBestRoundFlight(Integer roundNum, Integer eventId);
    ResponseEntity<VaultResponseDTO> updateEventRoundStatus(Integer roundNum, Integer eventId);
    void synchronizeEventRound(Integer roundNum, Integer eventId);
    void desynchronizeEventRound(Integer roundNum, Integer eventId);

    ResponseEntity<?> synchronizeAfterOffline(List<RoundDTO> rounds);

    List<RoundDTO> getDtos();
}
