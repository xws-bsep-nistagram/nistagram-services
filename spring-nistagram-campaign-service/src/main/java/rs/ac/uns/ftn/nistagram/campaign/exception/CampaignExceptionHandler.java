package rs.ac.uns.ftn.nistagram.campaign.exception;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class CampaignExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    String handleEntityNotFound(EntityNotFoundException e) {
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CampaignException.class)
    String handleCampaignException(CampaignException e) {
        log.info(e.getMessage());
        log.trace(e.getMessage(), e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    String handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage(), e);
        return "Internal server error has occurred!";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FeignException.NotFound.class)
    String handleDocumentNotFound(FeignException.NotFound e) {
        return "Document path not found.";
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(FeignException.Unauthorized.class)
    String handleUnauthorizedDBAccess(FeignException.Unauthorized e) {
        return "You do not have the permission to upload.";
    }
}
