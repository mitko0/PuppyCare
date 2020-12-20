package finki.das.puppycare.model.projection;

import finki.das.puppycare.model.PetReport;
import org.springframework.beans.factory.annotation.Value;

public interface RatingView {
    @Value("#{target.getReport()}")
    PetReport getReport();

    float getAverage();
}
