package finki.das.puppycare.model.key;

import finki.das.puppycare.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AuthorityKey implements Serializable {

    @Column(name = "username")
    protected String username;

    @Enumerated(EnumType.STRING)
    protected Role role;
}
