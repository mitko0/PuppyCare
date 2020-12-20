package finki.das.puppycare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import finki.das.puppycare.model.key.PetTermKey;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pet_terms")
@Entity
public class PetTerm {

    @EmbeddedId
    private PetTermKey id;

    private boolean seen = false;

    @Future(message = "Невалиден термин!")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_")
    private Date date;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("owner_id")
    @JoinColumn(name = "owner_id")
    private User owner;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("pet_id")
    @JoinColumn(name = "pet_id")
    private Pet pet;
}
