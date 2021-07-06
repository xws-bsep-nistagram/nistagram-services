package rs.ac.uns.ftn.nistagram.content.exception;

public class NotFollowingException extends NistagramException {
    public NotFollowingException(String follower, String followed) {
        super(follower + " does not follow " + followed);
    }
}
