package rs.ac.uns.ftn.nistagram.user.graph.controllers.payload;

import lombok.Data;

@Data
public class FollowerStatsResponse {

    private Long following;
    private Long followers;

}
