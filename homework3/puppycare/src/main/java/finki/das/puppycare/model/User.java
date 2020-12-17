package finki.das.puppycare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
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

    //@OneToMany(mappedBy = "user")
    //private List<Authority> authorities;

    @Enumerated(EnumType.STRING)
    Role role;

}
