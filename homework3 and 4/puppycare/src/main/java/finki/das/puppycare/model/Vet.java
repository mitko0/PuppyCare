package finki.das.puppycare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
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

    @JsonIgnore
    @OneToMany(mappedBy="vet", fetch = FetchType.LAZY)
    List<User> employees;

    @JsonIgnore
    @OneToMany(mappedBy="vet", fetch = FetchType.LAZY)
    List<PetReport> reports;

    @JsonIgnore
    @OneToMany(mappedBy="vet", fetch = FetchType.LAZY)
    List<Pet> pets;
}
