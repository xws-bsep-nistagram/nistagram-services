package rs.ac.uns.ftn.nistagram.user.graph.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserStats {

    private Long following;
    private Long followers;

}
