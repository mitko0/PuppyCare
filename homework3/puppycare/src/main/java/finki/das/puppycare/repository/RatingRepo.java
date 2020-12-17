package finki.das.puppycare.repository;

import finki.das.puppycare.model.Rating;
import finki.das.puppycare.model.RatingKey;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepo extends JpaRepository<Rating, RatingKey> {

    // findOrderedByValue(PageRequest.of(0,1));
    @Query("select r, avg(r.value) as average from Rating r group by r.id order by average")
    List<Rating> findOrderedByValue(Pageable pageable);
}
