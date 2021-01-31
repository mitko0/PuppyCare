package finki.das.puppycare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import finki.das.puppycare.Constants;
import finki.das.puppycare.model.enums.PetType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pets")
@Entity
public class Pet {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private PetType type;

    @Column(name = "images_location")
    private String imagesLocation;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vet_id")
    private Vet vet;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @JsonIgnore
    @OneToMany(mappedBy="pet", fetch = FetchType.LAZY)
    List<PetTerm> terms = new ArrayList<>();

    @Transient
    String[] images;

    @PostLoad
    public void loadImages() {
        images = Constants.fileDetails(name);
    }
}



