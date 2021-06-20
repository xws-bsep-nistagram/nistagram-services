package rs.ac.uns.ftn.nistagram.content.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

@ControllerAdvice
@Slf4j
public class ContentExceptionHandler {

    @ExceptionHandler(OwnershipException.class)
    public ResponseEntity<?> handleOwnershipError(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(ExistingEntityException.class)
    public ResponseEntity<?> handleExistingEntityErrors(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ProfileNotPublicException.class)
    public ResponseEntity<?> handleProfileNotPublicException(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(NistagramException.class)
    public ResponseEntity<?> handleApplicationErrors(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleOtherRuntimeErrors(RuntimeException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError().body("An internal server error occurred.");
    }

    @ExceptionHandler(UserBlockedException.class)
    public ResponseEntity<?> handleUserBlockedException(UserBlockedException e) {
        log.warn(e.getMessage());
        return ResponseEntity.ok(new ArrayList<>());
    }

}
