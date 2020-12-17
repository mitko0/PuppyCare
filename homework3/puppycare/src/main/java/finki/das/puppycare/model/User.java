package finki.das.puppycare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User {

    @Id
    private String username;

    private String password;

    //@OneToMany(mappedBy = "user")
    //private List<Authority> authorities;

    @Enumerated(EnumType.STRING)
    Role role;

}
