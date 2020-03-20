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
    public List<Round> findByPilotId(int pilotId) {
        return roundRepository.findByRoundIdPilotIdOrderByRoundIdRoundNum(pilotId);
    }

    @Override
    public List<Round> findByRoundNum(int roundNumber) {
        return roundRepository.findByRoundIdRoundNumOrderBySeconds(roundNumber);
    }

    @Override
    public Round save(Round round) {
//        round.setFlightTime(LocalDateTime.now());
        return roundRepository.save(round);
    }


}
