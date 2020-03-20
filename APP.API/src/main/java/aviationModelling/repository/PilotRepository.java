package aviationModelling.repository;

import aviationModelling.entity.Pilot;
import aviationModelling.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PilotRepository extends JpaRepository<Pilot, Integer> {

    List<Pilot> findAllByOrderByLastName();

    @Query("SELECT p FROM Pilot p " +
            "JOIN Round r ON p.id = r.roundId.pilotId " +
            "WHERE r.roundId.roundNum = :roundNum " +
            "ORDER BY r.seconds DESC")
    List<Pilot> findPilotsWithFinishedFlight(@Param("roundNum") Integer roundNum);

    @Query("SELECT pil FROM Pilot pil WHERE pil.id NOT IN " +
            "(SELECT p.id FROM Pilot p JOIN Round r ON p.id = r.roundId.pilotId WHERE r.roundId.roundNum = :round)" +
            "ORDER BY pil.lastName")
    List<Pilot> findPilotsWithUnfinishedFlight(@Param("round") Integer round);

    @Query("SELECT p FROM Pilot p " +
            "JOIN Round r ON p.id = r.roundId.pilotId " +
            "WHERE r.roundId.roundNum = :round AND r.group = :group " +
            "ORDER BY r.seconds, p.lastName")
    List<Pilot> findPilotsWithFinishedFlightGroupedByGroup(@Param("round") Integer round, @Param("group") String group);

//    @Query("SELECT p FROM Pilot p " +
//            "JOIN Round r ON p.id = r.roundId.pilotId " +
//            "WHERE r.roundId.roundNum = :round " +
//            "ORDER BY r.group, r.seconds, p.lastName")
//    List<Pilot> findPilotsByGroup(@Param("round") Integer round);
}
