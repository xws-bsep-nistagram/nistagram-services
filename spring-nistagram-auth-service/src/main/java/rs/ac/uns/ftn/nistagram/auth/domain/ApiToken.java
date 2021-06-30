package rs.ac.uns.ftn.nistagram.auth.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Token which uniquely identifies an external project's API calls.
 */
@Entity
@Getter
@Setter
public class ApiToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(unique = true)
    private String packageName;
    private String agent;
}
