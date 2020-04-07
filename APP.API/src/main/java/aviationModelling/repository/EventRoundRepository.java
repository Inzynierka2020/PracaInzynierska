package aviationModelling.repository;

import aviationModelling.entity.EventRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRoundRepository extends JpaRepository<EventRound, Integer> {

    @Query("SELECT er.id FROM EventRound er " +
            "WHERE er.roundNum = :roundNum " +
            "AND er.eventId = :eventId")
    Integer getEventRoundId(@Param("roundNum") Integer roundNum, @Param("eventId") Integer eventId);

    @Query("SELECT er FROM EventRound er " +
            "WHERE er.eventId = :eventId")
    List<EventRound> findAll(Integer eventId);
}
