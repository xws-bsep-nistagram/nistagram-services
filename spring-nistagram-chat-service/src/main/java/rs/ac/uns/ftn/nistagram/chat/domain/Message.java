package rs.ac.uns.ftn.nistagram.chat.domain;

import java.time.Instant;

public abstract class Message {
    private User sender;
    private User receiver;
    private Instant timestamp;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
