package rs.ac.uns.ftn.nistagram.chat.exceptions;

public class OperationNotPermittedException extends BusinessException {

    public OperationNotPermittedException() {
        super();
    }

    public OperationNotPermittedException(String message) {
        super(message);
    }


}
