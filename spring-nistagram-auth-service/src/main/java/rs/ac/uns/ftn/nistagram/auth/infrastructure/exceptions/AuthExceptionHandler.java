package rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AuthExceptionHandler {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(BadCredentialsException.class)
    String handleBadCredentials(BadCredentialsException e) {
        log.info(e.getMessage());
        log.trace(e.getMessage(), e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(DisabledException.class)
    String handleDisabled(DisabledException e) {
        log.info(e.getMessage());
        log.trace(e.getMessage(), e);
        return "Account not activated!";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthenticationException.class)
    String handleE(AuthenticationException e) {
        log.info(e.getMessage());
        log.trace(e.getMessage(), e);
        return "Cannot authenticate user!";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordResetException.class)
    String handleE(PasswordResetException e) {
        log.info(e.getMessage());
        log.trace(e.getMessage(), e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    String handleExceptionDefault(RuntimeException e) {
        log.error(e.getMessage(), e);
        return "Internal server error has occurred!";
    }

}
