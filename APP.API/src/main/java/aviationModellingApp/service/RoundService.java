package aviationModellingApp.service;

import aviationModellingApp.entity.Round;

import java.util.List;

public interface RoundService {

    List<Round> findByRoundIdPilotId(int pilotId);
    List<Round> findByRoundIdRoundNum(int roundNumber);

}
