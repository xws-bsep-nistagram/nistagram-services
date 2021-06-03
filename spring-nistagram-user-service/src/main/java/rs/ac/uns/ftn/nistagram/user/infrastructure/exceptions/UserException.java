package rs.ac.uns.ftn.nistagram.user.infrastructure.exceptions;

public class UserException extends RuntimeException {

    public UserException() {
        super();
    }

    public UserException(String message) {
        super(message);
    }

}
