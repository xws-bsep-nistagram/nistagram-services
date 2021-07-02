package rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.apitokens;

public class ApplicationAlreadyRegisteredException extends RuntimeException {

    public ApplicationAlreadyRegisteredException(String packageName) {
        super("Application package " + packageName + " has already been registered for an API token.");
    }

}
