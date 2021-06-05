package rs.ac.uns.ftn.nistagram.content.domain.core.post.collection;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "collections")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomPostCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String owner;
    private String name;
}
