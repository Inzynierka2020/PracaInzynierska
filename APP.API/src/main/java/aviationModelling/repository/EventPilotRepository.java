package aviationModelling.repository;

import aviationModelling.entity.EventPilot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventPilotRepository extends JpaRepository<EventPilot, Integer> {


}
