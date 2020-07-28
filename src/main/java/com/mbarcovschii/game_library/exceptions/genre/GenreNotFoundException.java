package com.mbarcovschii.game_library.exceptions.genre;

public class GenreNotFoundException extends RuntimeException {

    private Long genreId;

    public GenreNotFoundException(Long genreId) {

        super("Could not find genre with id " + genreId);
        this.genreId = genreId;
    }

    public Long getGenreId() {
        return genreId;
    }
}
