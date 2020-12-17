package finki.das.puppycare.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
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
