package aviationModelling.repository;

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

    @Query("SELECT r.flights FROM Round r " +
            "WHERE r.roundNum = :roundNum")
    List<Flight> findRoundFlights(@Param("roundNum") Integer roundNum);

    @Query("SELECT r.flights FROM Round r " +
            "WHERE r.roundNum = :roundNum AND r.isCancelled = false")
    List<Flight> findUncancelledRoundFlights(@Param("roundNum") Integer roundNum);
}
