package finki.das.puppycare.repository;

import finki.das.puppycare.model.PetTerm;
import finki.das.puppycare.model.PetTermKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetTermRepo extends JpaRepository<PetTerm, PetTermKey> {
}
