package rs.ac.uns.ftn.nistagram.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ChatExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    String handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.info(e.getMessage());
        log.trace(e.getMessage(), e);

        return e.getFieldErrors().stream()
                .map(error -> error.getField() + ":" + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    String handleNotFound(EntityNotFoundException e) {
        log.info(e.getMessage());
        log.trace(e.getMessage(), e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OperationNotPermittedException.class)
    String handleOperationNotPermittedException(OperationNotPermittedException e) {
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
