package rs.ac.uns.ftn.nistagram.user.infrastructure.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(RegistrationException.class)
    String handleBadCredentials(RegistrationException e) {
        log.info(e.getMessage());
        log.trace(e.getMessage(), e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(BannedException.class)
    String handleBannedException(BannedException e) {
        log.info(e.getMessage());
        log.trace(e.getMessage(), e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    String handleNotFound(EntityNotFoundException e) {
        log.info(e.getMessage());
        log.trace(e.getMessage(), e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException.class)
    String handleUserException(UserException e) {
        log.info(e.getMessage());
        log.trace(e.getMessage(), e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    String handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.info(e.getMessage());
        log.trace(e.getMessage(), e);

        return e.getMessage();
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    String handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage(), e);
        return "Internal server error has occured!";
    }

}
