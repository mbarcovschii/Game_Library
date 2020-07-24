package com.mbarcovschii.game_library.exceptions;

public class DeveloperNotFoundException extends RuntimeException {

    public DeveloperNotFoundException(Long developerId) {
        super("Could not find developer: " + developerId);
    }
}
