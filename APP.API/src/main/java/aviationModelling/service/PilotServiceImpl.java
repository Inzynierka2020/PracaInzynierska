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
}
