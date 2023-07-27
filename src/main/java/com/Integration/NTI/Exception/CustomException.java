package com.Integration.NTI.Exception;

import org.springframework.http.HttpStatus;

public class CustomException extends Exception{
    private final String description;
    private final HttpStatus status;

    public CustomException(String description, HttpStatus status) {
        this.description = description;
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
