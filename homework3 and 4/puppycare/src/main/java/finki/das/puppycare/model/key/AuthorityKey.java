package finki.das.puppycare.model.key;

import finki.das.puppycare.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

/**
 * Composite key class for authorities
 */
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
