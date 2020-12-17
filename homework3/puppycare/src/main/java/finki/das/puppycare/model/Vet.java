package finki.das.puppycare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vets")
@Entity
public class Vet {
    @Id
    private Long id;

    @Column(name = "lat")
    private double latitude;

    @Column(name = "lon")
    private double longitude;

    @OneToMany(mappedBy="vet")
    List<PetReport> reports;

    @OneToMany(mappedBy="vet")
    List<Pet> pets;
}
