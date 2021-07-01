package rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.auth;

public class JwtException extends AuthException {

    public JwtException() {
        super("An exception has occurred during JWT operation!");
    }

    public JwtException(String message) {
        super(message);
    }

}
