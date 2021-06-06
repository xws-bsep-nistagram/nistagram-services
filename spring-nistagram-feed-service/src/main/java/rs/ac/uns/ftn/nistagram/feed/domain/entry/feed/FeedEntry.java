package rs.ac.uns.ftn.nistagram.feed.domain.entry.feed;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.feed.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class FeedEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "username")
    private User user;
    private String publisher;
    private LocalDateTime createdAt;

    public FeedEntry(String publisher, LocalDateTime createdAt){
        this.publisher = publisher;
        this.createdAt = createdAt;
    }

}
