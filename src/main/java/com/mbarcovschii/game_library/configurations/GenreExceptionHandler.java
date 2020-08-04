package com.mbarcovschii.game_library.configurations;

import com.mbarcovschii.game_library.exceptions.genre.ExistingGenreException;
import com.mbarcovschii.game_library.exceptions.genre.GenreNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GenreExceptionHandler {

    @ExceptionHandler(GenreNotFoundException.class)
    public ResponseEntity<?> handleGenreNotFoundException(
            GenreNotFoundException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("id", ex.getGenreId());
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistingGenreException.class)
    public ResponseEntity<?> handleExistingGenreNameException(
            ExistingGenreException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("fieldName", "genreName");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
