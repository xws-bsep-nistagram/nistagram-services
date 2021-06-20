package rs.ac.uns.ftn.nistagram.user.graph.controllers.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MutedRelationshipResponse {
    private Boolean muted;
}
