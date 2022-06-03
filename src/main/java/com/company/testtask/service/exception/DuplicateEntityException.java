package com.company.testtask.service.exception;

import lombok.Getter;

@Getter
public class DuplicateEntityException extends RuntimeException {

    private String messageKey;
    private String name;

    public DuplicateEntityException(String messageKey, String name) {
        this.messageKey = messageKey;
        this.name = name;
    }

    public DuplicateEntityException() {
    }

    public DuplicateEntityException(String message) {
        super(message);
    }

    public DuplicateEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateEntityException(Throwable cause) {
        super(cause);
    }

}