package rs.ac.uns.ftn.nistagram.user.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileStatsDTO {

    private Long following;
    private Long followers;
    private long postCount;

}
