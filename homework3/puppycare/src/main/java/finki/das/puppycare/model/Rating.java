package finki.das.puppycare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import finki.das.puppycare.model.key.RatingKey;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @MapsId("report_id")
    @JoinColumn(name = "report_id")
    private PetReport report;
}
