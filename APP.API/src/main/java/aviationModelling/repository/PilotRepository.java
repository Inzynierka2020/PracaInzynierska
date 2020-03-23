package aviationModelling.repository;

import aviationModelling.entity.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PilotRepository extends JpaRepository<Pilot, Integer> {

    List<Pilot> findAllByOrderByLastName();

    @Query("SELECT p FROM Pilot p " +
            "JOIN Flight f ON p.id = f.flightId.pilotId " +
            "WHERE f.flightId.roundNum = :roundNum " +
            "ORDER BY f.seconds DESC")
    List<Pilot> findPilotsWithFinishedFlight(@Param("roundNum") Integer roundNum);

    @Query("SELECT pil FROM Pilot pil WHERE pil.id NOT IN " +
            "(SELECT p.id FROM Pilot p JOIN Flight f ON p.id = f.flightId.pilotId WHERE f.flightId.roundNum = :roundNum)" +
            "ORDER BY pil.lastName")
    List<Pilot> findPilotsWithUnfinishedFlight(@Param("roundNum") Integer roundNum);

    @Query("SELECT p FROM Pilot p " +
            "JOIN Flight f ON p.id = f.flightId.pilotId " +
            "WHERE f.flightId.roundNum = :round AND f.group = :group " +
            "ORDER BY f.seconds, p.lastName")
    List<Pilot> findPilotsWithFinishedFlightGroupedByGroup(@Param("round") Integer round, @Param("group") String group);

}
