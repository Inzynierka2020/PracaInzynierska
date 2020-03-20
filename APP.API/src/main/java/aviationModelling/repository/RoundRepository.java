package aviationModelling.repository;

import aviationModelling.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundRepository extends JpaRepository<Round, Round.RoundId> {

    List<Round> findByRoundIdPilotIdOrderByRoundIdRoundNum(int pilotId);
    List<Round> findByRoundIdRoundNumOrderBySeconds(int roundNumber);
    List<Double> find();

}
