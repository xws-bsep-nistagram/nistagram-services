package rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions;

import com.auth0.jwt.exceptions.JWTDecodeException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.apitokens.ApplicationAlreadyRegisteredException;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.auth.BannedException;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.auth.PasswordResetException;

@Slf4j
@RestControllerAdvice
public class ControllerAdvisor {

    // AUTH

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
    @ExceptionHandler(BannedException.class)
    String handleBanned(BannedException e) {
        log.info(e.getMessage());
        log.trace(e.getMessage(), e);
        return "Account is banned!";
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

    @ExceptionHandler(JWTDecodeException.class)
    String handleJwtDecodeException(JWTDecodeException e) {
        log.info(e.getMessage());
        log.trace(e.getMessage(), e);
        return "Jwt token invalid!";
    }

    // API TOKEN

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApplicationAlreadyRegisteredException.class)
    String entityNotFoundHandler(ApplicationAlreadyRegisteredException e) {
        return e.getMessage();
    }

    // GENERAL

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    String handleExceptionDefault(RuntimeException e) {
        log.error(e.getMessage(), e);
        return "Internal server error has occurred!";
    }
}
