package rs.ac.uns.ftn.nistagram.chat.domain;

import java.time.LocalDateTime;

public abstract class Message {
    private String sender;
    private String receiver;
    private LocalDateTime time;
}
