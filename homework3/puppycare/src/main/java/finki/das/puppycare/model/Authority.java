package finki.das.puppycare.model;


import finki.das.puppycare.model.key.AuthorityKey;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authorities")
// un-comment this for separate authorities table
//@Entity
public class Authority {

    @EmbeddedId
    private AuthorityKey id;

    @ManyToOne
    @MapsId("username")
    @JoinColumn(name = "username", insertable = false, updatable = false)
    private User user;
}
