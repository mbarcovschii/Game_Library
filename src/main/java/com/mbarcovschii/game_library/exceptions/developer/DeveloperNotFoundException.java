package com.mbarcovschii.game_library.exceptions.developer;

public class DeveloperNotFoundException extends RuntimeException {

    private Long developerId;

    public DeveloperNotFoundException(Long developerId) {

        super("Could not find developer: " + developerId);
        this.developerId = developerId;
    }

    public Long getDeveloperId() {
        return developerId;
    }
}
