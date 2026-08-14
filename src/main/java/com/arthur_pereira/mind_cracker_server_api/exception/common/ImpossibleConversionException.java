package com.arthur_pereira.mind_cracker_server_api.exception.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ImpossibleConversionException extends RuntimeException {
    public ImpossibleConversionException(String message) {
        super(message);
    }
}
