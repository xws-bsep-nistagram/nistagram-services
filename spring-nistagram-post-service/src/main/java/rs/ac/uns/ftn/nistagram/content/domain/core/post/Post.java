package rs.ac.uns.ftn.nistagram.content.domain.core.post;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.content.domain.core.UserContent;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.Comment;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.HashTag;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.UserInteraction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post extends UserContent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Post.MediaLink> mediaUrls;
    @OneToMany(mappedBy = "post")
    private List<UserInteraction> userInteractions;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
    @OneToMany(mappedBy = "post")
    private List<HashTag> hashTags;

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != Post.class) return false;
        else return this.id == ((Post) obj).getId();
    }

    @Entity
    @Table(name = "media_links")
    @Getter
    @Setter
    public static class MediaLink {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private long id;
        @NotEmpty
        private String url;
        @ManyToOne
        private Post post;
    }
}
