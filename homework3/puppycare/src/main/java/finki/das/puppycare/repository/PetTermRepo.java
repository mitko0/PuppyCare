package finki.das.puppycare.repository;

import finki.das.puppycare.model.PetTerm;
import finki.das.puppycare.model.key.PetTermKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PetTermRepo extends JpaRepository<PetTerm, PetTermKey> {

    @Query("select t from PetTerm t where t.pet.vet.id = :vetId and t.seen = false")
    List<PetTerm> findPetTermsForVet(Long vetId);
}
