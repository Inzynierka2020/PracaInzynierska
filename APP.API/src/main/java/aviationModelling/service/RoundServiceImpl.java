package aviationModelling.service;

import aviationModelling.entity.Round;
import aviationModelling.repository.RoundRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoundServiceImpl implements RoundService {

    private RoundRepository roundRepository;

    public RoundServiceImpl(RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
    }

    @Override
    public List<Round> findByRoundIdPilotId(int pilotId) {
        return roundRepository.findByRoundIdPilotId(pilotId);
    }

    @Override
    public List<Round> findByRoundIdRoundNum(int roundNumber) {
        return roundRepository.findByRoundIdRoundNum(roundNumber);
    }

    @Override
    public Round save(Round round) {
//        round.setFlightTime(LocalDateTime.now());
        return roundRepository.save(round);
    }
}
