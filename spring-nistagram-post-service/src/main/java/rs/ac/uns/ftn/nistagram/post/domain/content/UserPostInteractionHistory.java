package rs.ac.uns.ftn.nistagram.post.domain.content;

import rs.ac.uns.ftn.nistagram.post.domain.content.post.Post;

import java.util.List;

public class UserPostInteractionHistory {
    private String username;
    private List<Post> liked;
    private List<Post> disliked;
}
