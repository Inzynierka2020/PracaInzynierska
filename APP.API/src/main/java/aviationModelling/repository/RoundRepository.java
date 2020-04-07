package aviationModelling.repository;

import aviationModelling.entity.EventPilot;
import aviationModelling.entity.EventRound;
import aviationModelling.entity.Flight;
import aviationModelling.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundRepository extends JpaRepository<Round, Integer> {

    Round findByRoundNum(Integer roundNum);

    @Query("SELECT er FROM EventRound er " +
            "WHERE er.eventId = :eventId")
    List<EventRound> findAll(@Param("eventId") Integer eventId);

    @Query("SELECT er FROM EventRound er " +
            "WHERE er.eventId = :eventId " +
            "AND er.roundNum = :roundNum")
    EventRound findEventRound(@Param("roundNum") Integer roundNum, @Param("eventId") Integer eventId);

    @Query("SELECT er.flights FROM EventRound er " +
            "WHERE er.roundNum = :roundNum " +
            "AND er.eventId = :eventId")
    List<Flight> findRoundFlights(@Param("roundNum") Integer roundNum, @Param("eventId") Integer eventId);

    @Query("SELECT er.roundNum FROM EventRound er " +
            "WHERE er.isCancelled = false " +
            "AND er.eventId = :eventId")
    List<Integer> getRoundNumbers(@Param("eventId") Integer eventId);

}
