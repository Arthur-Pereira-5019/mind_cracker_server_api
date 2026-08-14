package com.arthur_pereira.mind_cracker_server_api.controller;

import com.arthur_pereira.mind_cracker_server_api.data.common.ExceptionResult;
import com.arthur_pereira.mind_cracker_server_api.exception.common.*;
import com.arthur_pereira.mind_cracker_server_api.exception.match.InexistingPlayerPosition;
import com.arthur_pereira.mind_cracker_server_api.exception.match.UnableToJoinMatchException;
import com.arthur_pereira.mind_cracker_server_api.exception.security.TokenGenerationException;
import com.arthur_pereira.mind_cracker_server_api.exception.security.UnauthorizedActionException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestController
@ControllerAdvice
public class ExceptionHandlerController {

    @Autowired
    private HttpServletRequest srequest;

//
//    @ExceptionHandler(AuthorizationDeniedException.class)
//    public final Object handleAuthorizationDenied(Exception ex, WebRequest request) {
//        if(srequest.getRequestURL().toString().contains("/api")) {
//            ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
//            return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
//        }
//        return new RedirectView("/");
//    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResult> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResult exceptionResponse = new ExceptionResult(ex.getMessage(), new Date(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }

    @ExceptionHandler(DomainException.class)
    public final ResponseEntity<ExceptionResult> handleDomainExceptions(Exception ex, WebRequest request) {
        ExceptionResult exceptionResponse = new ExceptionResult(ex.getMessage(), new Date(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }

    @ExceptionHandler(BadLoadAttemptException.class)
    public final ResponseEntity<ExceptionResult> handleBadLoadAttempt(Exception ex, WebRequest request) {
        ExceptionResult exceptionResponse = new ExceptionResult(ex.getMessage(), new Date(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResult> handleResourceNotFound(Exception ex, WebRequest request) {
        ExceptionResult exceptionResponse = new ExceptionResult(ex.getMessage(), new Date(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public final ResponseEntity<ExceptionResult> handleUnauthorizedAction(Exception ex, WebRequest request) {
        ExceptionResult exceptionResponse = new ExceptionResult(ex.getMessage(), new Date(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }

    @ExceptionHandler(DuplicatedResourceException.class)
    public final ResponseEntity<ExceptionResult> handleDuplicatedResourceException(Exception ex, WebRequest request) {
        ExceptionResult exceptionResponse = new ExceptionResult(ex.getMessage(), new Date(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }

    @ExceptionHandler(TokenGenerationException.class)
    public final ResponseEntity<ExceptionResult> handleTokenGenerationException(Exception ex, WebRequest request) {
        ExceptionResult exceptionResponse = new ExceptionResult(ex.getMessage(), new Date(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }

    @ExceptionHandler(UnableToJoinMatchException.class)
    public final ResponseEntity<ExceptionResult> handleUnableToJoinMatchException(Exception ex, WebRequest request) {
        ExceptionResult exceptionResponse = new ExceptionResult(ex.getMessage(), new Date(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }

    @ExceptionHandler(InexistingPlayerPosition.class)
    public final ResponseEntity<ExceptionResult> handleInexistingPlayerPosition(Exception ex, WebRequest request) {
        ExceptionResult exceptionResponse = new ExceptionResult(ex.getMessage(), new Date(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }

    @ExceptionHandler(ImpossibleConversionException.class)
    public final ResponseEntity<ExceptionResult> handleImpossibleConversionException(Exception ex, WebRequest request) {
        ExceptionResult exceptionResponse = new ExceptionResult(ex.getMessage(), new Date(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }



}
