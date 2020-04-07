package aviationModelling.repository;

import aviationModelling.entity.EventPilot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventPilotRepository extends JpaRepository<EventPilot, Integer> {

    @Query("SELECT ep.id FROM EventPilot ep " +
            "WHERE ep.pilotId = :pilotId " +
            "AND ep.eventId = :eventId")
    Integer getEventPilotId(@Param("pilotId") Integer pilotId, @Param("eventId") Integer eventId);

    @Query("SELECT ep FROM EventPilot ep " +
            "WHERE ep.eventId = :eventId")
    List<EventPilot> findAll(@Param("eventId") Integer eventId);
}
