package finki.das.puppycare.repository;

import finki.das.puppycare.model.PetReport;
import finki.das.puppycare.model.Vet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepo extends JpaRepository<PetReport, Long> {

    List<PetReport> findByVet(Vet vet);

}
