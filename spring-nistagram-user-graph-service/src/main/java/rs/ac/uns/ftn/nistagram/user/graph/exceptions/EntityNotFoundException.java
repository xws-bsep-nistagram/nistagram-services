package rs.ac.uns.ftn.nistagram.user.graph.exceptions;

public class EntityNotFoundException extends BusinessException{

    public EntityNotFoundException(){
        super("Entity not found");
    }

    public EntityNotFoundException(String message){
        super(message);
    }

}
