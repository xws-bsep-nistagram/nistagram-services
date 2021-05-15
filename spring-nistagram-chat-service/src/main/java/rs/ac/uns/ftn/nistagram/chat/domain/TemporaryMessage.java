package rs.ac.uns.ftn.nistagram.chat.domain;

import java.time.Duration;

public class TemporaryMessage extends AttachmentMessage {
    private Duration duration;

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
