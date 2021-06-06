package rs.ac.uns.ftn.nistagram.feed.messaging.payload.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class PrivacyData {

    private boolean profilePrivate;
    private boolean messagesFromNonFollowersAllowed;
    private boolean taggable;


}
