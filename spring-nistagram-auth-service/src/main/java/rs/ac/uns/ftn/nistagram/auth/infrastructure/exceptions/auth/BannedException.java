package rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.auth;

public class BannedException extends RuntimeException {

    public BannedException(String message) {
        super(message);
    }
}
