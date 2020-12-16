package finki.das.puppycare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pet_reports")
@Entity
public class PetReport {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    String message;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private double lat;

    private double lon;

    private boolean done = false;

    @Column(name = "customer_serves")
    private boolean customerServes = false;

    @Enumerated(EnumType.STRING)
    private PetType type;

    @ManyToOne
    @JoinColumn(name = "vet_id")
    private Vet vet;
}
