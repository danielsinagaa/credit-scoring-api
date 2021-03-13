package com.enigma.creditscoringapi.exceptions;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {

    private HttpStatus status;

    public ApplicationException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
