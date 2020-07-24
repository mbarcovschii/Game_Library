package com.mbarcovschii.game_library.exceptions;

public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(Long gameId) {
        super("Could not find game: " + gameId);
    }
}
