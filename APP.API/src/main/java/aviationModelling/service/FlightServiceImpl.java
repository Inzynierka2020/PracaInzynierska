package aviationModelling.service;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.VaultResponseDTO;
import aviationModelling.entity.Flight;
import aviationModelling.exception.CustomNotFoundException;
import aviationModelling.mapper.FlightMapper;
import aviationModelling.repository.FlightRepository;
import aviationModelling.repository.PilotRepository;
import aviationModelling.repository.RoundRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private FlightRepository flightRepository;
    private VaultService vaultService;
    private PilotRepository pilotRepository;
    private RoundRepository roundRepository;

    public FlightServiceImpl(FlightRepository flightRepository, VaultService vaultService, PilotRepository pilotRepository, RoundRepository roundRepository) {
        this.flightRepository = flightRepository;
        this.vaultService = vaultService;
        this.pilotRepository = pilotRepository;
        this.roundRepository = roundRepository;
    }

    @Override
    public ResponseEntity<FlightDTO> save(FlightDTO flightDTO) {
        if(flightDTO.isDns() || flightDTO.isDnf()) {
            flightDTO.setSeconds(0F);
        }

        Integer eventPilotId = pilotRepository.getEventPilotId(flightDTO.getPilotId(), flightDTO.getEventId());
        Integer eventRoundId = roundRepository.getEventRoundId(flightDTO.getRoundNum(), flightDTO.getEventId());

        Flight flight = FlightMapper.MAPPER.toFlight(flightDTO);
        flight.setFlightId(new Flight.FlightId(eventPilotId, eventRoundId));
        flightRepository.save(flight);
        return new ResponseEntity<>(flightDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> delete(FlightDTO flightDTO) {
        Integer eventPilotId = pilotRepository.getEventPilotId(flightDTO.getPilotId(), flightDTO.getEventId());
        Integer eventRoundId = roundRepository.getEventRoundId(flightDTO.getRoundNum(), flightDTO.getEventId());
        Flight flight = FlightMapper.MAPPER.toFlight(flightDTO);
        flight.setFlightId(new Flight.FlightId(eventPilotId, eventRoundId));
        flightRepository.delete(flight);
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<VaultResponseDTO> postScore(Integer roundNum, Integer pilotId, Integer eventId) {
        final Integer highestValidRoundNumber = findHighestValidRoundNumber(eventId);
        if(roundNum > (highestValidRoundNumber + 1)) {
            return new ResponseEntity<>(new VaultResponseDTO(400, "1000", "Cannot create a round that's more than one ahead of the last."), HttpStatus.BAD_REQUEST);
        }
        Flight flight = findFlight(roundNum, pilotId, eventId);
        VaultResponseDTO response = vaultService.postScore(FlightMapper.MAPPER.toFlightDTO(flight));
        if (response.getResponse_code().equals(0)) {
            throw new RuntimeException(response.getError_string());
        }
        flight.setSynchronized(true);
        flightRepository.save(flight);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public Flight findFlight(Integer roundNum, Integer pilotId, Integer eventId) {
        final Integer eventRoundId = roundRepository.getEventRoundId(roundNum, eventId);
        final Integer eventPilotId = pilotRepository.getEventPilotId(pilotId, eventId);
        return flightRepository.findFlight(eventRoundId, eventPilotId, eventId);
    }

    public Integer findHighestValidRoundNumber(Integer eventId) {
        final List<Integer> roundNumbers = roundRepository.getAllSynchronizedRoundNumbers(eventId);
        Collections.sort(roundNumbers);
        if (roundNumbers.size()>0) {
            Integer highestRound = 0;
            for (Integer roundNumber : roundNumbers) {
                if(roundNumber-highestRound<2) {
                    highestRound = roundNumber;
                } else {
                    final Integer highestRound1 = highestRound;
                    return highestRound1;
                }
            }
            return highestRound;
        }
        return 0;
    }
}
