package finki.das.puppycare.model.projection;

import finki.das.puppycare.model.PetReport;
import org.springframework.beans.factory.annotation.Value;

/**
 * Projection interface for selecting pet report and average rating
 */
public interface RatingView {
    @Value("#{target.getReport()}")
    PetReport getReport();

    float getAverage();
}
