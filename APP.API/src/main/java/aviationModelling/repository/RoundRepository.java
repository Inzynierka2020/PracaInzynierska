package aviationModelling.repository;

import aviationModelling.entity.Flight;
import aviationModelling.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundRepository extends JpaRepository<Round, Integer> {

    @Query("SELECT r FROM Round r " +
            "WHERE r.eventId = :eventId")
    List<Round> findAll(@Param("eventId") Integer eventId);

    Round findByRoundNumAndEventId(Integer roundNum, Integer eventId);

    @Query("SELECT r.flights FROM Round r " +
            "WHERE r.roundNum = :roundNum " +
            "AND r.eventId = :eventId")
    List<Flight> findRoundFlights(@Param("roundNum") Integer roundNum, @Param("eventId") Integer eventId);

    @Query("SELECT r.roundNum FROM Round r " +
            "WHERE r.isCancelled = false " +
            "AND r.eventId = :eventId")
    List<Integer> getRoundNumbers(@Param("eventId") Integer eventId);
}
