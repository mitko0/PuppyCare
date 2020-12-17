package finki.das.puppycare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
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

    @ManyToOne
    @JoinColumn(name = "vet_id")
    private Vet vet;

    @OneToMany(mappedBy = "report")
    private List<Rating> ratings = new ArrayList<>();
}
