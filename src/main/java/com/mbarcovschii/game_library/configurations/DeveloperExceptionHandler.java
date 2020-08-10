package com.mbarcovschii.game_library.configurations;

import com.mbarcovschii.game_library.exceptions.developer.DeveloperGameListShouldBeEmptyException;
import com.mbarcovschii.game_library.exceptions.developer.DeveloperNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class DeveloperExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DeveloperNotFoundException.class)
    public ResponseEntity<?> handleDeveloperNotFoundException(
            DeveloperNotFoundException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("id", ex.getDeveloperId());
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DeveloperGameListShouldBeEmptyException.class)
    public ResponseEntity<?> handleDeveloperGamesListShouldBeEmptyException(
            DeveloperGameListShouldBeEmptyException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
