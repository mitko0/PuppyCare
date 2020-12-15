package finki.das.puppycare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "vets")
public class Vet {
    @Id
    private Long id;

    @Column(name = "lat")
    private double latitude;

    @Column(name = "lon")
    private double longitude;

    @OneToMany(mappedBy="vet")
    List<PetReport> reports;
}
