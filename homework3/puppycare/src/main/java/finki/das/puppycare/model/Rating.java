package finki.das.puppycare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ratings")
@Entity
public class Rating {

    @EmbeddedId
    private RatingKey id;

    @Column(columnDefinition = "real check (value >= 0 and value <= 5)")
    private float value = 0;

    private String message;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("report_id")
    @JoinColumn(name = "report_id")
    private PetReport report;
}
