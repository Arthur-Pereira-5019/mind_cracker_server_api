package com.arthur_pereira.mind_cracker_server_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadLoadAttempt extends RuntimeException {
    public BadLoadAttempt(String message) {
        super(message);
    }
}
