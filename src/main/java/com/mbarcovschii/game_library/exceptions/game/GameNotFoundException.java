package com.mbarcovschii.game_library.exceptions.game;

public class GameNotFoundException extends RuntimeException {

    private Long gameId;

    public GameNotFoundException(Long gameId) {

        super("Could not find game with id " + gameId);
        this.gameId = gameId;
    }

    public Long getGameId() {
        return gameId;
    }
}
