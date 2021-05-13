package rs.ac.uns.ftn.nistagram.post.domain.content;

import java.time.Instant;

public class Report <T extends UserContent>{

    private Instant reportedAt;
    private String reportedBy;
    private String reportingReason;
    private T userContent;


}
