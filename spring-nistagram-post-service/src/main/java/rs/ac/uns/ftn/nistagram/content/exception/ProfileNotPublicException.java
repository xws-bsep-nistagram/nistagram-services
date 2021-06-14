package rs.ac.uns.ftn.nistagram.content.exception;

public class ProfileNotPublicException extends NistagramException{

    public ProfileNotPublicException(String username){
        super(String.format("'%s's profile is not public.", username));
    }

}
