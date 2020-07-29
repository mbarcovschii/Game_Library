package com.mbarcovschii.game_library.configurations;

import com.mbarcovschii.game_library.exceptions.PartialUpdateIsEmptyException;
import com.mbarcovschii.game_library.exceptions.RequiredFieldValueIsNotDefinedException;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        String exMessage = ex.getMessage();
        Pattern pattern = Pattern.compile("\\[\"\\w+\"]");
        Matcher matcher = pattern.matcher(exMessage);

        if (matcher.find()) {
            String field = exMessage.substring(matcher.start(), matcher.end());
            body.put("field", field.substring(2, field.length() - 2));
        }

        pattern = Pattern.compile("\"\\w+\"");
        matcher = pattern.matcher(exMessage);

        if (matcher.find()) {
            String incorrectValue = exMessage.substring(matcher.start(), matcher.end());
            body.put("incorrectValue", incorrectValue.substring(1, incorrectValue.length() - 1));
        }

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}