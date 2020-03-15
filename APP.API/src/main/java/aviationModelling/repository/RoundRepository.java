package aviationModelling.repository;

import aviationModelling.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundRepository extends JpaRepository<Round, Round.RoundId> {

    List<Round> findByRoundIdPilotId(int pilotId);
    List<Round> findByRoundIdRoundNum(int roundNumber);
}
