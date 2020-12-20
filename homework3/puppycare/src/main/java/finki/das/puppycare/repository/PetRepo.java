package finki.das.puppycare.repository;

import finki.das.puppycare.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepo extends JpaRepository<Pet, Long> {
}
