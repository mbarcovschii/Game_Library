package com.mbarcovschii.game_library.configurations;

import com.mbarcovschii.game_library.exceptions.ExistingGenreException;
import com.mbarcovschii.game_library.exceptions.GenreNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GenreExceptionHandler {

    @ResponseBody
    @ExceptionHandler(GenreNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String genreNotFoundHandler(GenreNotFoundException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ExistingGenreException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String existingGenreNameHandler(ExistingGenreException exception) {
        return exception.getMessage();
    }
}
