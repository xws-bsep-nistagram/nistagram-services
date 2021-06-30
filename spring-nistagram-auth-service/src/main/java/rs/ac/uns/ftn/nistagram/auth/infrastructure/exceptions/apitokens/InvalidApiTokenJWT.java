package rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.apitokens;

public class InvalidApiTokenJWT extends RuntimeException {
    public InvalidApiTokenJWT() {
        super("Invalid API Token JWT.");
    }
}
