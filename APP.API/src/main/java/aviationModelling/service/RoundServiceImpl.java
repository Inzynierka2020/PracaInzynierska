package aviationModelling.service;

import aviationModelling.entity.Round;
import aviationModelling.repository.RoundRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoundServiceImpl implements RoundService {

    private RoundRepository roundRepository;

    public RoundServiceImpl(RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
    }

    @Override
    public Round findByRoundNum(Integer roundNum) {
        return roundRepository.findByRoundNum(roundNum);
    }

    @Override
    public ResponseEntity<String> create(Integer roundNum) {
        Round round = new Round();
        round.setRoundNum(roundNum);
        round.setIsCancelled(false);
        roundRepository.save(round);
        return new ResponseEntity<>("Round "+roundNum+" created!", HttpStatus.OK);
    }
}
