package rs.ac.uns.ftn.nistagram.post.domain.content;

import java.util.List;

public class Post extends UserContent{

    private List<String> mediaUrl;
    private List<String> likedBy;
    private List<String> dislikedBy;
    private List<Comment> comments;

}
