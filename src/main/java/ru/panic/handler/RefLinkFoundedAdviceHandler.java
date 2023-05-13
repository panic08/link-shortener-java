package ru.panic.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.panic.exception.RefLinkFoundedException;

@RestControllerAdvice
public class RefLinkFoundedAdviceHandler {
    @ResponseBody
    @ExceptionHandler(RefLinkFoundedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleInvalidCredentialsException(RefLinkFoundedException exception){
        return exception.getMessage();
    }
}
