package rs.ac.uns.ftn.nistagram.content.controller.dto.output;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoryHighlightOverviewDTO {
    private long id;
    private String name;
    private List<StoryOverviewDTO> stories;
}
