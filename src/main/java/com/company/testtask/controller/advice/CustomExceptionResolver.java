package com.company.testtask.controller.advice;


import com.company.testtask.service.exception.DuplicateEntityException;
import com.company.testtask.service.exception.EntityNotFoundException;
import com.company.testtask.service.exception.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionResolver {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Message> handleEntityNotFound(EntityNotFoundException ex) {
        String message = ex.getMessageKey();
        Object value = ex.getValue();
        return new ResponseEntity<>(new Message(message, value), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<Message> handleDuplicateEntity(DuplicateEntityException ex) {
        String message = ex.getMessageKey();
        String value = ex.getName();
        return new ResponseEntity<>(new Message(message, value), HttpStatus.CONFLICT);
    }
}
