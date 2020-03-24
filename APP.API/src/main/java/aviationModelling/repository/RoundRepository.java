package aviationModelling.repository;

import aviationModelling.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundRepository extends JpaRepository<Round, Integer> {

    Round findByRoundNum(Integer roundNum);

    Integer countRoundsByIsCancelledFalse();
}
