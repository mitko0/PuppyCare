package finki.das.puppycare.model;

import finki.das.puppycare.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pets")
@Entity
public class Pet {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private PetType type;

    @Column(name = "images_location")
    private String imagesLocation;

    @ManyToOne
    @JoinColumn(name = "vet_id")
    private Vet vet;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy="pet")
    List<PetTerm> terms = new ArrayList<>();

    @Transient
    List<File> images;

    @PostLoad
    public void loadImages() {
        images = Constants.fileDetails(name);
    }
}



