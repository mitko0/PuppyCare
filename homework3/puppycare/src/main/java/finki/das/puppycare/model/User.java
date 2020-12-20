package finki.das.puppycare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import finki.das.puppycare.model.enums.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User {

    @Size(min = 3, message = "Корисничкото име не смее да е пократко од 3 знака!")
    @Id
    private String username;

    @Size(min = 5, message = "Лозинката не смее да е пократка од 5 знака!")
    private String password;

    @Email
    @NotEmpty
    private String email;

    @Enumerated(EnumType.STRING)
    Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vet_id")
    Vet vet;

    @JsonIgnore
    @OneToMany(mappedBy="owner", fetch = FetchType.LAZY)
    List<Pet> pets = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy="owner", fetch = FetchType.LAZY)
    List<PetTerm> terms = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Rating> ratings = new ArrayList<>();

    //@OneToMany(mappedBy = "user")
    //private List<Authority> authorities;
}
