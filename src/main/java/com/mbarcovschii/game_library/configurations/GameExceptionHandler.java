package com.mbarcovschii.game_library.configurations;

import com.mbarcovschii.game_library.exceptions.game.GameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GameExceptionHandler extends ResponseEntityExceptionHandler {

//    @ResponseBody
//    @ExceptionHandler(GameNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    String gameNotFoundHandler(GameNotFoundException exception) {
//
//        return exception.getMessage();
//    }

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<?> handleGameNotFoundException(
            GameNotFoundException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("id", ex.getGameId());
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}

