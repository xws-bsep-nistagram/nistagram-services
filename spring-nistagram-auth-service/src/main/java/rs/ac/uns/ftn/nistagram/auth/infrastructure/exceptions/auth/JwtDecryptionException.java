package rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.auth;

public class JwtDecryptionException extends JwtException {

    public JwtDecryptionException() {
        super("An error has occurred while decrypting a JWT token!");
    }

}
