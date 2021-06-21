package rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions;

public class AuthException extends RuntimeException {

    public AuthException() {
        super("Nistagram auth service exception has occurred!");
    }

    public AuthException(String message) {
        super(message);
    }

}
