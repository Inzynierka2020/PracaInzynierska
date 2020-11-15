package aviationModelling.repository;

import aviationModelling.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Flight.FlightId> {



    @Query("SELECT f FROM Flight f " +
            "WHERE f.flightId.eventRoundId = :roundNum " +
            "AND f.flightId.eventPilotId = :pilotId " +
            "AND f.eventRound.eventId = :eventId")
    Flight findFlight(@Param("roundNum") Integer eventRoundNum, @Param("pilotId") Integer eventPilotId,
                      @Param("eventId") Integer eventId);

    @Query("SELECT f FROM Flight f " +
            "WHERE f.eventRound.eventId = :eventId " +
            "AND f.eventRound.isCancelled = false " +
            "AND f.seconds = (SELECT min(fl.seconds) " +
            "                   FROM Flight fl " +
            "                   WHERE fl.seconds > 0)")
    List<Flight> findBestFromEvent(@Param("eventId") Integer eventId);

    @Query("SELECT f FROM Flight f " +
            "WHERE f.eventRound.eventId = :eventId " +
            "AND f.eventRound.isCancelled = false " +
            "AND f.eventRound.roundNum = :roundNum " +
            "AND f.group = :group " +
            "AND f.seconds = (SELECT min(fl.seconds) " +
            "                   FROM Flight fl " +
            "                   WHERE fl.seconds > 0 " +
            "                   AND f.eventRound.roundNum = :roundNum" +
            "                   AND fl.group = :group)")
    List<Flight> findBestRoundGroupsFlight(@Param("roundNum") Integer roundNum, @Param("eventId") Integer eventId, @Param("group") String group);
}
