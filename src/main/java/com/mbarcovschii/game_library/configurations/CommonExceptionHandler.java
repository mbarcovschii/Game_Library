package com.mbarcovschii.game_library.configurations;

import com.mbarcovschii.game_library.exceptions.PartialUpdateIsEmptyException;
import com.mbarcovschii.game_library.exceptions.RequiredFieldValueIsNotDefinedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(PartialUpdateIsEmptyException.class)
    public ResponseEntity<?> handlePartialUpdateIsEmptyException(
            PartialUpdateIsEmptyException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RequiredFieldValueIsNotDefinedException.class)
    public ResponseEntity<?> handleRequiredFieldValueIsNotDefinedException(
            RequiredFieldValueIsNotDefinedException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("fieldName", ex.getFieldName());
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}