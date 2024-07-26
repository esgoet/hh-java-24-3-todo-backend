package com.github.esgoet.hhjava243todobackend.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleException(NoSuchElementException e) {
        return new ErrorMessage(
                e.getMessage(),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleException(NoResourceFoundException e) {
        return new ErrorMessage(
                e.getMessage(),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleException(Exception e) {
        return new ErrorMessage(
                e.getMessage(),
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }

}
