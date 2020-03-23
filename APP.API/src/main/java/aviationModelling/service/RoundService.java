package aviationModelling.service;

import aviationModelling.entity.Round;
import org.springframework.http.ResponseEntity;

public interface RoundService {

    Round findByRoundNum(Integer roundNum);

    ResponseEntity<String> create(Integer roundNum);
}
