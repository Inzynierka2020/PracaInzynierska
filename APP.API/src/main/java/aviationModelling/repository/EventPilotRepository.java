package aviationModelling.repository;

import aviationModelling.entity.EventPilot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventPilotRepository extends JpaRepository<EventPilot, Integer> {


}
