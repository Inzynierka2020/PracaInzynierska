package aviationModelling.service;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.PilotDTO;
import aviationModelling.entity.Flight;
import aviationModelling.entity.Pilot;
import aviationModelling.exception.CustomNotFoundException;
import aviationModelling.mapper.FlightMapper;
import aviationModelling.mapper.PilotMapper;
import aviationModelling.repository.PilotRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PilotServiceImpl implements PilotService {

    private PilotRepository pilotRepository;

    public PilotServiceImpl(PilotRepository pilotRepository) {
        this.pilotRepository = pilotRepository;
    }

    @Override
    public List<PilotDTO> findAll() {
        List<Pilot> pilots = pilotRepository.findAllByOrderByLastName();
        if (pilots.size() == 0) {
            throw new CustomNotFoundException("Pilot list not found");
        } else {
            return PilotMapper.MAPPER.toPilotListDTO(pilots);
        }
    }

    @Override
    public List<PilotDTO> findAllOrderByScore() {
        List<Pilot> pilots = pilotRepository.findAllByOrderByScoreDesc();
        if (pilots.size() == 0) {
            throw new CustomNotFoundException("Pilot list not found");
        } else {
            return PilotMapper.MAPPER.toPilotListDTO(pilots);
        }
    }

    @Override
    public ResponseEntity<PilotDTO> save(PilotDTO pilotDTO) {
        Optional<Pilot> result = pilotRepository.findById(pilotDTO.getId());
        pilotRepository.save(PilotMapper.MAPPER.toPilot(pilotDTO));
        if (!result.isPresent()) {
            return new ResponseEntity<>(pilotDTO, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(pilotDTO, HttpStatus.OK);
        }
    }

    @Override
    public PilotDTO findById(int id) {
        Optional<Pilot> result = pilotRepository.findById(id);
        Pilot pilot = null;

        if (result.isPresent()) {
            pilot = result.get();
        } else {
            throw new CustomNotFoundException("Pilot with pilot ID " + id + " not found.");
        }
        return PilotMapper.MAPPER.toPilotDTO(pilot);
    }

    @Override
    public List<FlightDTO> findPilotFlights(Integer pilotId) {
        List<Flight> flightList = pilotRepository.findPilotFlights(pilotId);
        if (flightList.size() == 0) {
            throw new CustomNotFoundException("Pilot's " + pilotId + " flight list not found");
        } else {
            return FlightMapper.MAPPER.toFlightDTOList(flightList);
        }
    }

    @Override
    public List<FlightDTO> findUncancelledAndFinishedPilotFlights(Integer pilotId) {
        List<Flight> flightList = pilotRepository.findUncancelledAndFinishedPilotFlights(pilotId);
        if (flightList.size() == 0) {
            throw new CustomNotFoundException("Pilot's " + pilotId + " finished flight list not found");
        } else {
            return FlightMapper.MAPPER.toFlightDTOList(flightList);
        }
    }

    @Override
    public List<PilotDTO> findPilotsWithFinishedFlight(Integer roundNum) {
        List<Pilot> pilots = pilotRepository.findPilotsWithFinishedFlight(roundNum);
        if (pilots.size() == 0) {
            throw new CustomNotFoundException("Pilot list is empty (round="+roundNum+")");
        } else {
            return PilotMapper.MAPPER.toPilotListDTO(pilots);
        }
    }

    @Override
    public List<PilotDTO> findPilotsWithUnfinishedFlight(Integer roundNum) {
        List<Pilot> pilots = pilotRepository.findPilotsWithUnfinishedFlight(roundNum);
        if (pilots.size() == 0) {
            throw new CustomNotFoundException("Pilot list is empty (round="+roundNum+")");
        } else {
            return PilotMapper.MAPPER.toPilotListDTO(pilots);
        }
    }

    @Override
    public List<PilotDTO> findPilotsWithFinishedFlightGroupedByGroup(Integer roundNum, String group) {
        List<Pilot> pilots = pilotRepository.findPilotsWithFinishedFlightGroupedByGroup(roundNum, group);
        if (pilots.size() == 0) {
            throw new CustomNotFoundException("Pilot list of round " + roundNum + " and group " + group + " not found");
        } else {
            return PilotMapper.MAPPER.toPilotListDTO(pilots);
        }
    }

    @Override
    public Float findBestPilotTime(Integer pilotId) {
        return pilotRepository.findBestPilotTime(pilotId);
    }

    @Override
    public ResponseEntity<String> saveAll(List<Pilot> pilotList) {
        pilotRepository.saveAll(pilotList);
        return new ResponseEntity<>("Pilot list saved successfully", HttpStatus.OK);
    }
}
