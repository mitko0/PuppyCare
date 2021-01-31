package finki.das.puppycare.repository;

import finki.das.puppycare.model.Rating;
import finki.das.puppycare.model.key.RatingKey;
import finki.das.puppycare.model.projection.RatingView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepo extends JpaRepository<Rating, RatingKey> {

    // findOrderedByValue(PageRequest.of(0,1));
    @Query("select r, avg(r.value) as average from Rating r group by r.report.vet.id order by average")
    List<Rating> findAverageRating(Pageable pageable);

    // @Query("select r.id, avg(r.value) as average from Rating r group by r.report.vet.id order by average")
    @Query("select r.report.vet.id, avg(r.value) as average from Rating r group by r.report.vet.id order by average")
    List<RatingView> findAverageRating();

    List<Rating> findRatingById_ReportId(Long reportId);
}
