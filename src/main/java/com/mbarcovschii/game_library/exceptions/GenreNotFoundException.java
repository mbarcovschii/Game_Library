package com.mbarcovschii.game_library.exceptions;

public class GenreNotFoundException extends RuntimeException {

    public GenreNotFoundException(Long genreId) {
        super("Genre not found: " + genreId);
    }
}
