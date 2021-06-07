package rs.ac.uns.ftn.nistagram.feed.exceptions;

public class EntityAlreadyExistsException extends BusinessException{

    public EntityAlreadyExistsException(){
        super("Entity already exists");
    }

    public EntityAlreadyExistsException(String message){
        super(message);
    }

}
