package com.arthur_pereira.mind_cracker_server_api.exception.game;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnableToJoinGameException extends RuntimeException {
    public UnableToJoinGameException(String message) {
        super(message);
    }
}
