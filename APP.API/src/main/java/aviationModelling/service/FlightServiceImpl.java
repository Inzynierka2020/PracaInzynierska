package aviationModelling.service;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.VaultResponseDTO;
import aviationModelling.entity.Flight;
import aviationModelling.exception.CustomNotFoundException;
import aviationModelling.mapper.FlightMapper;
import aviationModelling.repository.FlightRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private FlightRepository flightRepository;
    private RoundService roundService;
    private VaultService vaultService;

    public FlightServiceImpl(FlightRepository flightRepository, RoundService roundService, VaultService vaultService) {
        this.flightRepository = flightRepository;
        this.roundService = roundService;
        this.vaultService = vaultService;
    }

    @Override
    public ResponseEntity<FlightDTO> save(FlightDTO flightDTO) {
        Flight flight = FlightMapper.MAPPER.toFlight(flightDTO);
        flightRepository.save(flight);
        return new ResponseEntity<>(flightDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<VaultResponseDTO> postScore(FlightDTO flightDTO) {
        VaultResponseDTO response = vaultService.postScore(flightDTO);
        if (response.getResponse_code().equals(0)) {
            throw new RuntimeException(response.getError_string());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @Override
//    public ResponseEntity<List<FlightDTO>> saveAll(List<FlightDTO> flightList) {
//        flightRepository.saveAll(FlightMapper.MAPPER.toFlightList(flightList));
//        return new ResponseEntity<>(flightList, HttpStatus.CREATED);
//    }
//
//    @Override
//    public FlightDTO findBestTime(Integer eventId) {
//        Flight flight = flightRepository.findBestTime(eventId);
//        if(flight==null) {
//            throw new CustomNotFoundException("Flight with best time not found");
//        }
//        return FlightMapper.MAPPER.toFlightDTO(flight);
//    }
//
//    @Override
//    public FlightDTO findFlight(Integer roundNum, Integer pilotId, Integer eventId) {
//        Flight flight = flightRepository.findFlight(roundNum, pilotId, eventId);
//        if(flight==null) {
//            throw new CustomNotFoundException("Flight (round = "+roundNum+", pilot ID = "+pilotId+") not found.");
//        }
//        return FlightMapper.MAPPER.toFlightDTO(flight);
//    }

}
