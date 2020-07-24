package com.mbarcovschii.game_library.exceptions;

public class ExistingGenreException extends RuntimeException {

    public ExistingGenreException(String genreName) {
        super("Genre name is already taken: " + genreName);
    }
}
