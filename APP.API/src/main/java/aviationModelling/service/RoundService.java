package aviationModelling.service;

import aviationModelling.entity.Round;

import java.util.List;

public interface RoundService {

    List<Round> findByRoundIdPilotId(int pilotId);
    List<Round> findByRoundIdRoundNum(int roundNumber);
    Round save(Round round);
}
