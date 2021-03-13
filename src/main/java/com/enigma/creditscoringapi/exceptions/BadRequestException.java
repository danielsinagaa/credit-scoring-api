package com.enigma.creditscoringapi.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApplicationException {

    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST, "error." + HttpStatus.BAD_REQUEST.value());
    }
}

