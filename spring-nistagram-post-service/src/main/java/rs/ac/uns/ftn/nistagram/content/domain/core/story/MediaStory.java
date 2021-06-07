package rs.ac.uns.ftn.nistagram.content.domain.core.story;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class MediaStory extends Story {
    private String mediaUrl;
}
