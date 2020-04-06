package aviationModelling.repository;

import aviationModelling.entity.Flight;
import aviationModelling.entity.Pilot;
import aviationModelling.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface PilotRepository extends JpaRepository<Pilot, Integer> {

    List<Pilot> findAllByOrderByLastName();
    List<Pilot> findByEventIdOrderByLastName(Integer eventId);
    List<Pilot> findAllByOrderByScoreDesc();
    List<Pilot> findByEventIdOrderByScoreDesc(Integer eventId);

    @Query("SELECT p FROM Pilot p " +
            "WHERE p.id = :pilotId " +
            "AND p.eventId = :eventId")
    Pilot findByPilotIdAndEventId(Integer pilotId, Integer eventId);

    @Query("SELECT p.flights FROM Pilot p " +
            "WHERE p.id = :pilotId " +
            "AND p.eventId = :eventId")
    List<Flight> findPilotFlights(@Param("pilotId") Integer pilotId, @Param("eventId") Integer eventId);

    @Query("SELECT f FROM Pilot p " +
            "JOIN Flight f ON p.id = f.flightId.pilotId " +
            "WHERE p.id = :pilotId " +
            "AND f.round.isCancelled = false " +
            "AND f.round.isFinished = true " +
            "AND p.eventId = :eventId " +
            "ORDER BY f.flightId.roundNum")
    List<Flight> findUncancelledAndFinishedPilotFlights(@Param("pilotId") Integer pilotId, @Param("eventId") Integer eventId);

    @Query("SELECT p FROM Pilot p " +
            "JOIN Flight f ON p.id = f.flightId.pilotId " +
            "WHERE f.flightId.roundNum = :roundNum " +
            "AND p.eventId = :eventId " +
            "ORDER BY f.score DESC, p.lastName")
    List<Pilot> findPilotsWithFinishedFlight(@Param("roundNum") Integer roundNum, @Param("eventId") Integer eventId);


    @Query("SELECT pil FROM Pilot pil WHERE pil.id NOT IN " +
            "(SELECT p.id FROM Pilot p JOIN Flight f ON p.id = f.flightId.pilotId " +
            "WHERE f.flightId.roundNum = :roundNum)" +
            "AND pil.eventId = :eventId " +
            "ORDER BY pil.lastName")
    List<Pilot> findPilotsWithUnfinishedFlight(@Param("roundNum") Integer roundNum, @Param("eventId") Integer eventId);

    @Query("SELECT p FROM Pilot p " +
            "JOIN Flight f ON p.id = f.flightId.pilotId " +
            "WHERE f.flightId.roundNum = :round " +
            "AND f.group = :group " +
            "AND p.eventId = :eventId " +
            "ORDER BY f.score DESC, p.lastName")
    List<Pilot> findPilotsWithFinishedFlightGroupedByGroup(@Param("round") Integer round, @Param("group") String group, @Param("eventId") Integer eventId);


    @Query("SELECT min(f.seconds) FROM Pilot p " +
            "JOIN Flight f ON p.id = f.flightId.pilotId " +
            "WHERE p.id = :pilotId " +
            "AND p.eventId = :eventId " +
            "AND f.round.isCancelled = false " +
            "AND f.seconds>0")
    Float findBestPilotTime(@Param("pilotId") Integer pilotId, @Param("eventId") Integer eventId);


}
