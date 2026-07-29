package com.arthur_pereira.mind_cracker_server_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadLoadAttemptException extends RuntimeException {
    public BadLoadAttemptException(String message) {
        super(message);
    }
}
