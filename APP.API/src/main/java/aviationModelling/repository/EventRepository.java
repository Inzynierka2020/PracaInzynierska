package aviationModelling.repository;

import aviationModelling.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("SELECT e FROM Event e " +
            "WHERE e.eventId = :eventId")
    Event findByEventId(@Param("eventId") Integer eventId);
}
