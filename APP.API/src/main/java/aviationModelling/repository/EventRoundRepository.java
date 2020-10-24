package aviationModelling.repository;

import aviationModelling.entity.EventRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRoundRepository extends JpaRepository<EventRound, Integer> {

    EventRound findByRoundNumAndEventId(Integer roundNum, Integer eventId);
}
