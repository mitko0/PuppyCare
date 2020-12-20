package finki.das.puppycare.repository;

import finki.das.puppycare.model.Authority;
import finki.das.puppycare.model.key.AuthorityKey;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;


// this is used when there is separate table for authorities
@Profile("with_auth_table")
public interface AuthorityRepo extends JpaRepository<Authority, AuthorityKey> {
}
