package rs.ac.uns.ftn.nistagram.content.exception;

public class NistagramException extends RuntimeException {

    public NistagramException() {
        super("Nistagram application has run into an error.");
    }

    public NistagramException(String message) {
        super(message);
    }
}
