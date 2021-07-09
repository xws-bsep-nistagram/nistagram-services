package rs.ac.uns.ftn.nistagram.user.graph.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recommendation {

    private User recommendation;
    private List<User> mutualConnections;

    public Recommendation(User recommendation) {
        this.recommendation = recommendation;
        this.mutualConnections = new ArrayList<>();
    }

    public List<String> getConnectionsUsername() {
        return mutualConnections
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }

}
