package aviationModelling.repository;

import aviationModelling.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Flight.FlightId> {



    @Query("SELECT f FROM Flight f " +
            "WHERE f.flightId.eventRoundId = :roundNum " +
            "AND f.flightId.eventPilotId = :pilotId " +
            "AND f.eventRound.eventId = :eventId")
    Flight findFlight(@Param("roundNum") Integer eventRoundNum, @Param("pilotId") Integer eventPilotId,
                      @Param("eventId") Integer eventId);
}
