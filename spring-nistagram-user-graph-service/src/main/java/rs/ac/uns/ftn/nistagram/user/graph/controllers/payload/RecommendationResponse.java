package rs.ac.uns.ftn.nistagram.user.graph.controllers.payload;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResponse {

    private String recommendationUsername;
    private List<String> mutualConnectionsUsername;

}
