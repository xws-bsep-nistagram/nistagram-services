package rs.ac.uns.ftn.nistagram.content.messaging.payload.post;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostEventPayload {

    private String author;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime time;
    private Long contentId;
    private PostPayloadType postPayloadType;

}
