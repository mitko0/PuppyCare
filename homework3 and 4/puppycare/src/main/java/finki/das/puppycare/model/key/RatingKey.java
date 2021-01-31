package finki.das.puppycare.model.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Composite key class for ratings
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RatingKey implements Serializable {

    @Column(name = "user_id")
    private String userId;

    @Column(name = "report_id")
    private Long reportId;

}
