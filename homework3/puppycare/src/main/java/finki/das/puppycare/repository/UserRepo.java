package finki.das.puppycare.repository;

import finki.das.puppycare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
