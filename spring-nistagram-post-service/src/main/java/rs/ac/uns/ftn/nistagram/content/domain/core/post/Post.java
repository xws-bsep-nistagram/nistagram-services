package rs.ac.uns.ftn.nistagram.content.domain.core.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import rs.ac.uns.ftn.nistagram.content.domain.core.UserContent;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.collection.PostInCollection;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.collection.SavedPost;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.Comment;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.Tag;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.UserInteraction;
import rs.ac.uns.ftn.nistagram.content.domain.core.report.PostReport;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.ShareStory;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
@ToString
public class Post extends UserContent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Post.MediaLink> mediaUrls;
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<UserInteraction> userInteractions;
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Tag> tags;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<SavedPost> savedPosts;
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostInCollection> postsInCollections;
    @OneToMany(mappedBy = "sharedPost", cascade = CascadeType.REMOVE)
    private List<ShareStory> shareStories;
    @OneToMany(mappedBy = "reportedPost", cascade = CascadeType.REMOVE)
    private List<PostReport> postReports;

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != Post.class) return false;
        else return this.id == ((Post) obj).getId();
    }

    public boolean usersTagged() {
        return this.tags != null && !this.tags.isEmpty();
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
