package rs.ac.uns.ftn.nistagram.post.domain.content.post;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.post.domain.content.UserContent;
import rs.ac.uns.ftn.nistagram.post.domain.content.post.social.Comment;
import rs.ac.uns.ftn.nistagram.post.domain.content.post.social.HashTag;
import rs.ac.uns.ftn.nistagram.post.domain.content.post.social.UserInteraction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post extends UserContent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @OneToMany(mappedBy = "post")
    private List<Post.MediaLink> mediaUrls;
    @OneToMany(mappedBy = "post")
    private List<UserInteraction> userInteractions;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
    @OneToMany(mappedBy = "post")
    private List<HashTag> hashTags;

    @Entity
    @Table(name = "media_links")
    @Getter
    @Setter
    public static class MediaLink {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private long id;
        private String url;
        @OneToOne
        private Post post;
    }
}
