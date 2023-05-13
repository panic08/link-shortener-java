package ru.panic.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.panic.exception.RefLinkNotFoundException;

@RestControllerAdvice
public class RefLinkNotFoundedAdviceHandler {
    @ResponseBody
    @ExceptionHandler(RefLinkNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleInvalidCredentialsException(RefLinkNotFoundException exception){
        return exception.getMessage();
    }
}
