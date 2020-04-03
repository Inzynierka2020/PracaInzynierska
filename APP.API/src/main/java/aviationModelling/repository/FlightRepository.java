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
            "WHERE f.round.isCancelled = false AND f.seconds = " +
            "(SELECT min(fl.seconds) FROM Flight fl " +
            "WHERE fl.seconds>0 AND fl.round.isCancelled = false)")
    Flight findBestTime();

    Flight findByFlightIdRoundNumAndFlightIdPilotId(Integer roundNum, Integer pilotId);
}
