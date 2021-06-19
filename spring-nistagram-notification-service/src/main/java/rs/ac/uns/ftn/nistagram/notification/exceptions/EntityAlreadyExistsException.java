package rs.ac.uns.ftn.nistagram.notification.exceptions;

public class EntityAlreadyExistsException extends BusinessException {

    public EntityAlreadyExistsException() {
        super("Entity already exists");
    }

    public EntityAlreadyExistsException(String message) {
        super(message);
    }

}
