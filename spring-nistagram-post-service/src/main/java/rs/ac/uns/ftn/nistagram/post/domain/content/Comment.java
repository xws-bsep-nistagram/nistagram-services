package rs.ac.uns.ftn.nistagram.post.domain.content;

import java.time.LocalDateTime;

public class Comment {
    private Post post;
    private String writtenBy;
    private LocalDateTime writtenOn;
    private String content;
}
