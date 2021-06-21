package rs.ac.uns.ftn.nistagram.content.exception;

public class UserBlockedException extends NistagramException {
    public UserBlockedException(String subject, String target) {
        super(String.format("User %s is blocked by %s", target, subject));
    }
}
