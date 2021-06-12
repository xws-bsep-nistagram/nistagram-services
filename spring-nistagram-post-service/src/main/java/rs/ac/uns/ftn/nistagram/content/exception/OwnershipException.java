package rs.ac.uns.ftn.nistagram.content.exception;

public class OwnershipException extends NistagramException {

    public OwnershipException() {
        super("You are not the owner of this resource.");
    }
}
