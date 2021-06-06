package rs.ac.uns.ftn.nistagram.feed.domain.entry.feed;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.feed.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class FeedEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToMany
    @JoinColumn(name = "username")
    private List<User> users;
    private String publisher;
    private LocalDateTime createdAt;

    public FeedEntry(String publisher, LocalDateTime createdAt){
        this.publisher = publisher;
        this.createdAt = createdAt;
    }

    protected void addUser(User user) {
        if(this.users == null)
            this.users = new ArrayList<>();
        this.users.add(user);
    }
}
