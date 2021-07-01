package rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.apitokens;

public class ApiTokenNotFoundException extends RuntimeException {

    public ApiTokenNotFoundException() {
        super("Api token not found.");
    }
}
