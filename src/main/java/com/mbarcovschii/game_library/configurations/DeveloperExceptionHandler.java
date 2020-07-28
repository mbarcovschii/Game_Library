package com.mbarcovschii.game_library.configurations;

import com.mbarcovschii.game_library.exceptions.developer.DeveloperNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class DeveloperExceptionHandler {

    @ExceptionHandler(DeveloperNotFoundException.class)
    public ResponseEntity<?> handleDeveloperNotFoundException(
            DeveloperNotFoundException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("developerId", ex.getDeveloperId());
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
