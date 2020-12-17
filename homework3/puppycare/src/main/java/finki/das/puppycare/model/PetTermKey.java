package finki.das.puppycare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PetTermKey implements Serializable {

    @Column(name = "owner_id")
    private String ownerId;

    @Column(name = "pet_id")
    private Long petId;
}