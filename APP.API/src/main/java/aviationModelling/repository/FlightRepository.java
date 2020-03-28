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
            "WHERE f.seconds = (SELECT min(seconds) FROM Flight)")
    Flight findBestTime();

    @Query("SELECT f FROM Flight f " +
            "WHERE f.score = (SELECT max(score) FROM Flight)")
    Flight findBestScore();

    Flight findByFlightIdRoundNumAndFlightIdPilotId(Integer roundNum, Integer pilotId);
}
