package rs.ac.uns.ftn.nistagram.notification.domain;

import java.time.LocalDateTime;

public class Notification {
    private LocalDateTime time;
    private String to;
    private String from;

    protected String text;
    protected Notification.Type type;

    public enum Type {
        CHAT,
        POST,
        STORY,
        FOLLOW
    }

    public static class Builder {

    }
}
