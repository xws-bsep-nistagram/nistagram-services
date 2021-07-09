package rs.ac.uns.ftn.nistagram.campaign.report.controller;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvisor {

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
