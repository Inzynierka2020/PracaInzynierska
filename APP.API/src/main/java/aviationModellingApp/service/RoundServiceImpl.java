package aviationModellingApp.service;

import aviationModellingApp.entity.Round;
import aviationModellingApp.repository.RoundRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

}
