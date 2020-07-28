package com.mbarcovschii.game_library.exceptions.genre;

public class ExistingGenreException extends RuntimeException {

    private String genreName;

    public ExistingGenreException(String genreName) {

        super("Genre name is already taken: " + genreName);
        this.genreName = genreName;
    }

    public String getGenreName() {
        return genreName;
    }
}
