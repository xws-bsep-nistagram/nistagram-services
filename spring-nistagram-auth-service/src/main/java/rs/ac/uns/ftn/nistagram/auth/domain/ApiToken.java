package rs.ac.uns.ftn.nistagram.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Token which uniquely identifies an external project's API calls.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(unique = true)
    private String packageName;
    private String agent;
}
