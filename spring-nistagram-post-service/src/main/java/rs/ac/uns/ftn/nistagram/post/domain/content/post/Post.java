package rs.ac.uns.ftn.nistagram.post.domain.content.post;

import rs.ac.uns.ftn.nistagram.post.domain.content.UserContent;

import java.util.List;

public class Post extends UserContent {
    private List<String> mediaUrls;
    private List<String> likedBy;
    private List<String> dislikedBy;
    private List<Comment> comments;
}