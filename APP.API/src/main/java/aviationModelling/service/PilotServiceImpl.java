package aviationModelling.service;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.PilotDTO;
import aviationModelling.entity.EventPilot;
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
    public List<PilotDTO> findAll(Integer eventId) {
        return PilotMapper.MAPPER.toPilotDTOList(pilotRepository.findAll(eventId));
    }

    //    @Override
//    public List<PilotDTO> findAll(Integer eventId) {
//        List<Pilot> pilots = pilotRepository.findByEventIdOrderByLastName(eventId);
//        if (pilots.size() == 0) {
//            throw new CustomNotFoundException("Pilot list not found");
//        } else {
//            return PilotMapper.MAPPER.toPilotListDTO(pilots);
//        }
//    }
//
//    @Override
//    public List<PilotDTO> findAllOrderByScore(Integer eventId) {
//        List<Pilot> pilots = pilotRepository.findByEventIdOrderByScoreDesc(eventId);
//        if (pilots.size() == 0) {
//            throw new CustomNotFoundException("Pilot list not found");
//        } else {
//            return PilotMapper.MAPPER.toPilotListDTO(pilots);
//        }
//    }
//
//    @Override
//    public ResponseEntity<PilotDTO> save(PilotDTO pilotDTO) {
//        Pilot pilot = pilotRepository.findByPilotIdAndEventId(pilotDTO.getId(), pilotDTO.getEventId());
//        pilotRepository.save(PilotMapper.MAPPER.toPilot(pilotDTO));
//        if (pilot == null) {
//            return new ResponseEntity<>(pilotDTO, HttpStatus.CREATED);
//        } else {
//            return new ResponseEntity<>(pilotDTO, HttpStatus.OK);
//        }
//    }
//
//    @Override
//    public PilotDTO findByPilotIdAndEventId(Integer pilotId, Integer eventId) {
//        Pilot pilot = pilotRepository.findByPilotIdAndEventId(pilotId, eventId);
//        if (pilot == null) {
//            throw new CustomNotFoundException("Pilot with pilot ID " + pilotId + " not found.");
//        }
//        return PilotMapper.MAPPER.toPilotDTO(pilot);
//    }
//
//    @Override
//    public List<FlightDTO> findPilotFlights(Integer pilotId, Integer eventId) {
//        List<Flight> flightList = pilotRepository.findPilotFlights(pilotId, eventId);
//        if (flightList.size() == 0) {
//            throw new CustomNotFoundException("Pilot's " + pilotId + " flight list not found");
//        } else {
//            return FlightMapper.MAPPER.toFlightDTOList(flightList);
//        }
//    }
//
//    @Override
//    public List<FlightDTO> findUncancelledAndFinishedPilotFlights(Integer pilotId, Integer eventId) {
//        List<Flight> flightList = pilotRepository.findUncancelledAndFinishedPilotFlights(pilotId, eventId);
//        if (flightList.size() == 0) {
//            throw new CustomNotFoundException("Pilot's " + pilotId + " finished flight list not found");
//        } else {
//            return FlightMapper.MAPPER.toFlightDTOList(flightList);
//        }
//    }
//
//    @Override
//    public List<PilotDTO> findPilotsWithFinishedFlight(Integer roundNum, Integer eventId) {
//        List<Pilot> pilots = pilotRepository.findPilotsWithFinishedFlight(roundNum, eventId);
//        if (pilots.size() == 0) {
//            throw new CustomNotFoundException("Pilot list is empty (round=" + roundNum + ")");
//        } else {
//            return PilotMapper.MAPPER.toPilotListDTO(pilots);
//        }
//    }
//
//    @Override
//    public List<PilotDTO> findPilotsWithUnfinishedFlight(Integer roundNum, Integer eventId) {
//        List<Pilot> pilots = pilotRepository.findPilotsWithUnfinishedFlight(roundNum, eventId);
//        if (pilots.size() == 0) {
//            throw new CustomNotFoundException("Pilot list is empty (round=" + roundNum + ")");
//        } else {
//            return PilotMapper.MAPPER.toPilotListDTO(pilots);
//        }
//    }
//
//    @Override
//    public List<PilotDTO> findPilotsWithFinishedFlightGroupedByGroup(Integer roundNum, String group, Integer eventId) {
//        List<Pilot> pilots = pilotRepository.findPilotsWithFinishedFlightGroupedByGroup(roundNum, group, eventId);
//        if (pilots.size() == 0) {
//            throw new CustomNotFoundException("Pilot list of round " + roundNum + " and group " + group + " not found");
//        } else {
//            return PilotMapper.MAPPER.toPilotListDTO(pilots);
//        }
//    }
//
//    @Override
//    public Float findBestPilotTime(Integer pilotId, Integer eventId) {
//        return pilotRepository.findBestPilotTime(pilotId, eventId);
//    }
}
