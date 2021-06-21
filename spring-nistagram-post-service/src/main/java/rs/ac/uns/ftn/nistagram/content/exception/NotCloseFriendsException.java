package rs.ac.uns.ftn.nistagram.content.exception;

public class NotCloseFriendsException extends NistagramException {
    public NotCloseFriendsException(String follower, String followed) {
        super(follower + " and " + followed + " are not close friends.");
    }
}
