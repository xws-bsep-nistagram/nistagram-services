package rs.ac.uns.ftn.nistagram.post.domain.content.post;

import java.time.LocalDateTime;

public class Comment {
    private Post post;
    private String author;
    private LocalDateTime time;
    private String text;
}
