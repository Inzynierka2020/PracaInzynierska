package aviationModelling.service;

import aviationModelling.entity.Round;

import java.util.List;

public interface RoundService {

    List<Round> findByPilotId(int pilotId);
    List<Round> findByRoundNum(int roundNumber);
    Round save(Round round);
}
