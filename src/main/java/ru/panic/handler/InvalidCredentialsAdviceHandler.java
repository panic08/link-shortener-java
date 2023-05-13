package ru.panic.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.panic.exception.InvalidCredentialsException;

@RestControllerAdvice
public class InvalidCredentialsAdviceHandler {
    @ResponseBody
    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleInvalidCredentialsException(InvalidCredentialsException exception){
        return exception.getMessage();
    }
}