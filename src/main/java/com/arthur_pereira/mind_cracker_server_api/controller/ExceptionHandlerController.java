package com.arthur_pereira.mind_cracker_server_api.controller;

import com.arthur_pereira.mind_cracker_server_api.data.ExceptionResult;
import com.arthur_pereira.mind_cracker_server_api.exception.BadLoadAttempt;
import com.arthur_pereira.mind_cracker_server_api.exception.DomainException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.servlet.view.RedirectView;

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

    @ExceptionHandler(BadLoadAttempt.class)
    public final ResponseEntity<ExceptionResult> handleBadLoadAttempt(Exception ex, WebRequest request) {
        ExceptionResult exceptionResponse = new ExceptionResult(ex.getMessage(), new Date(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }



}
