package rs.ac.uns.ftn.nistagram.content.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ContentExceptionHandler {

    @ExceptionHandler(OwnershipException.class)
    public ResponseEntity<?> handleOwnershipError(Exception e, WebRequest req){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(ExistingEntityException.class)
    public ResponseEntity<?> handleExistingEntityErrors(Exception e, WebRequest req) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NistagramException.class)
    public ResponseEntity<?> handleApplicationErrors(Exception e, WebRequest req) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleOtherRuntimeErrors(Exception e, WebRequest req) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body("An error occurred.");
    }
}
