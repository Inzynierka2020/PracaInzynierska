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

    @Query("SELECT p.flights FROM Pilot p " +
            "WHERE p.id = :pilotId")
    List<Flight> findPilotFlights(@Param("pilotId") Integer pilotId);

    @Query("SELECT f FROM Pilot p " +
            "JOIN Flight f ON p.id = f.flightId.pilotId " +
            "WHERE p.id = :pilotId AND f.round.isCancelled = false " +
            "AND f.round.isFinished = true " +
            "ORDER BY f.flightId.roundNum")
    List<Flight> findUncancelledAndFinishedPilotFlights(@Param("pilotId") Integer pilotId);

    @Query("SELECT p FROM Pilot p " +
            "JOIN Flight f ON p.id = f.flightId.pilotId " +
            "WHERE f.flightId.roundNum = :roundNum " +
            "ORDER BY f.score DESC, p.lastName")
    List<Pilot> findPilotsWithFinishedFlight(@Param("roundNum") Integer roundNum);

    @Query("SELECT pil FROM Pilot pil WHERE pil.id NOT IN " +
            "(SELECT p.id FROM Pilot p JOIN Flight f ON p.id = f.flightId.pilotId " +
            "WHERE f.flightId.roundNum = :roundNum )" +
            "ORDER BY pil.lastName")
    List<Pilot> findPilotsWithUnfinishedFlight(@Param("roundNum") Integer roundNum);

    @Query("SELECT p FROM Pilot p " +
            "JOIN Flight f ON p.id = f.flightId.pilotId " +
            "WHERE f.flightId.roundNum = :round AND f.group = :group " +
            "ORDER BY f.score DESC, p.lastName")
    List<Pilot> findPilotsWithFinishedFlightGroupedByGroup(@Param("round") Integer round, @Param("group") String group);

    @Query("SELECT max(f.score) FROM Pilot p " +
            "JOIN Flight f ON p.id = f.flightId.pilotId " +
            "WHERE p.id = :pilotId AND f.round.isCancelled = false")
    Float findBestPilotScore(@Param("pilotId") Integer pilotId);

    @Query("SELECT min(f.seconds) FROM Pilot p " +
            "JOIN Flight f ON p.id = f.flightId.pilotId " +
            "WHERE p.id = :pilotId AND f.round.isCancelled = false")
    Float findBestPilotTime(@Param("pilotId") Integer pilotId);


}
