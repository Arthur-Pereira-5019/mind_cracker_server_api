package com.arthur_pereira.mind_cracker_server_api.exception.match;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InexistingPlayerPosition extends RuntimeException {
    public InexistingPlayerPosition(String message) {
        super(message);
    }
}
