package net.truksa.route.controller;

import net.truksa.route.controller.exceptions.ProblematicRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ProblematicRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String problematicRequestExceptionHandler(ProblematicRequestException ex) {
        return ex.getMessage();
    }
}
