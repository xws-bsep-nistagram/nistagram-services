package rs.ac.uns.ftn.nistagram.feed.exceptions;

public class BusinessException extends RuntimeException{

    public BusinessException(){
        super();
    }

    public BusinessException(String message){
        super(message);
    }

}
