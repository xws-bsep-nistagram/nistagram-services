package rs.ac.uns.ftn.nistagram.chat.domain;

import java.time.Duration;

public class TemporaryMessage extends Message {
    private Duration duration;
    private Attachment attachment;

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }
}
