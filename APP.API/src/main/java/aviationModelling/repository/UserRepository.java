package aviationModelling.repository;


import aviationModelling.entity.auth.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDAO, Integer> {
    UserDAO findByUsername(String username);
}
