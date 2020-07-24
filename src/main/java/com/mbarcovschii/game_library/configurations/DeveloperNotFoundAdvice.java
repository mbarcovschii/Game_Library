package com.mbarcovschii.game_library.configurations;

import com.mbarcovschii.game_library.exceptions.DeveloperNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DeveloperNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(DeveloperNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String developerNotFoundHandler(DeveloperNotFoundException exception) {
        return exception.getMessage();
    }
}
