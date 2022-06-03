package com.company.testtask.service.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private String messageKey;
    public Long id;

    public EntityNotFoundException(String messageKey, Long id) {
        this.messageKey = messageKey;
        this.id = id;
    }

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }
}
