package com.mbarcovschii.game_library.exceptions.developer;

public class DeveloperGameListShouldBeEmptyException extends RuntimeException {

    private Long developerId;

    public DeveloperGameListShouldBeEmptyException(Long developerId) {

        super("Developer [" + developerId + "] game list should be empty");
        this.developerId = developerId;
    }

    public Long getDeveloperId() {
        return developerId;
    }
}
