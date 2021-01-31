package finki.das.puppycare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import finki.das.puppycare.model.enums.PetType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pet_reports")
@Entity
public class PetReport {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotEmpty
    String title;

    @Column(name = "message_")
    String message;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_")
    private Date date;

    private double lat;

    private double lon;

    private boolean done = false;

    @Column(name = "customer_serves")
    private boolean customerServes = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_")
    private PetType type;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vet_id")
    private Vet vet;

    @JsonIgnore
    @OneToMany(mappedBy = "report", fetch = FetchType.LAZY)
    private List<Rating> ratings = new ArrayList<>();
}
