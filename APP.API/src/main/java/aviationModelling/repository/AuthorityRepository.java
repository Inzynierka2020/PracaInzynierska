package aviationModelling.repository;

import aviationModelling.entity.auth.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Authority findByName(String name);
}
