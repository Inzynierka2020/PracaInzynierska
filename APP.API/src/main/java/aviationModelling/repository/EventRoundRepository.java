package aviationModelling.repository;

import aviationModelling.entity.EventRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRoundRepository extends JpaRepository<EventRound, Integer> {

}
