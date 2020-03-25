package aviationModelling.repository;

import aviationModelling.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Flight.FlightId> {

    List<Flight> findByFlightIdPilotIdOrderByFlightIdRoundNum(int pilotId);
    List<Flight> findByFlightIdRoundNumOrderBySeconds(int roundNumber);




}
