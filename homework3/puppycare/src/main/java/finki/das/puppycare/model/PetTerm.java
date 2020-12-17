package finki.das.puppycare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pet_terms")
@Entity
public class PetTerm {

    @EmbeddedId
    private PetTermKey id;

    @Future(message = "Невалиден термин!")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_")
    private Date date;

    @ManyToOne
    @MapsId("owner_id")
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @MapsId("pet_id")
    @JoinColumn(name = "pet_id")
    private Pet pet;
}
