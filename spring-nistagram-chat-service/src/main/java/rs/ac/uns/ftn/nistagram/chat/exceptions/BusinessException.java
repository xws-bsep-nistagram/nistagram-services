package rs.ac.uns.ftn.nistagram.chat.exceptions;

public class BusinessException extends RuntimeException {
    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }
}
