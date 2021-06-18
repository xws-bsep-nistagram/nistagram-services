package rs.ac.uns.ftn.nistagram.user.domain.user;

import lombok.Data;

@Data
public class UserStats {

    private Long following;
    private Long followers;
    private Long postCount;

}
