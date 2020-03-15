package aviationModellingApp.repository;

import aviationModellingApp.entity.Round;
import aviationModellingApp.entity.RoundId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundRepository extends JpaRepository<Round, RoundId> {
    List<Round> findByRoundIdPilotId(int pilotId);
    List<Round> findByRoundIdRoundNum(int roundNumber);
}
