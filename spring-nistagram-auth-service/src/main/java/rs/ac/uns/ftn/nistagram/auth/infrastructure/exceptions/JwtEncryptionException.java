package rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions;

public class JwtEncryptionException extends JwtException {

    public JwtEncryptionException() {
        super("An error has occurred while decrypting a JWT token!");
    }

}
