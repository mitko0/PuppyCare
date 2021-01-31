package finki.das.puppycare.repository;

import finki.das.puppycare.model.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VetRepo extends JpaRepository<Vet, Long> {

    @Query(value = "select *, sqrt((:lat - lat) * (:lat - lat) + (:lon - lon) * (:lon - lon)) as d from vets order by d limit :limit", nativeQuery = true)
    List<Vet> nearestVets(double lat, double lon, int limit);

}
