package com.challenge.heroes.handler;

import com.challenge.heroes.exception.HeroNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(HeroNotFoundException.class)
    public void handleNotFound(HeroNotFoundException ex) {
        log.error("Requested hero not found");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleBlankName(IllegalArgumentException ex) {
        log.error("Bad request");
    }
}
