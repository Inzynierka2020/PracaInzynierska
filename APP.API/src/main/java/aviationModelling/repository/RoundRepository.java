package aviationModelling.repository;

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

//    @Query("SELECT er FROM EventRound er " +
//            "WHERE er.eventId = :eventId")
//    List<EventRound> findAll(@Param("eventId") Integer eventId);
//
//    @Query("SELECT er FROM Round r " +
//            "JOIN EventRound er ON r.roundNum=er.roundNum " +
//            "WHERE er.roundNum = :roundNum AND er.eventId = :eventId")
//    EventRound findByRoundNumAndEventId(@Param("roundNum") Integer roundNum, @Param("eventId") Integer eventId);
//
//    @Query("SELECT r FROM Round r " +
//            "JOIN EventRound er ON r.roundNum=er.roundNum " +
//            "WHERE r.roundNum = :roundNum AND er.eventId = :eventId")
//    Round findRoundByRoundNumAndEventId(@Param("roundNum") Integer roundNum, @Param("eventId") Integer eventId);
//
//    @Query("SELECT r.flights FROM Round r " +
//            "JOIN EventRound er ON r.roundNum=er.roundNum " +
//            "WHERE r.roundNum = :roundNum " +
//            "AND er.eventId = :eventId")
//    List<Flight> findRoundFlights(@Param("roundNum") Integer roundNum, @Param("eventId") Integer eventId);
//
//    @Query("SELECT er.roundNum FROM EventRound er " +
//            "WHERE er.isCancelled = false " +
//            "AND er.eventId = :eventId")
//    List<Integer> getRoundNumbers(@Param("eventId") Integer eventId);
}
