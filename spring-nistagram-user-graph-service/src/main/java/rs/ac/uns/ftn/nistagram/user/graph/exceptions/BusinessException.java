package rs.ac.uns.ftn.nistagram.user.graph.exceptions;

public class BusinessException extends RuntimeException{

    public BusinessException(){
        super();
    }

    public BusinessException(String message){
        super(message);
    }

}
