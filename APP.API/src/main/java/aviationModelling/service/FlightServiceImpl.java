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
    public ResponseEntity<VaultResponseDTO> postScore(FlightDTO flightDTO) {
        VaultResponseDTO response = vaultService.postScore(flightDTO);
        if (response.getResponse_code().equals(0)) {
            throw new RuntimeException(response.getError_string());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
