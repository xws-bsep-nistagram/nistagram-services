package rs.ac.uns.ftn.nistagram.content.exception;

public class NotFollowException extends NistagramException {
    public NotFollowException(String follower, String followed) {
        super(follower + " does not follow " + followed);
    }
}
