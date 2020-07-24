package com.mbarcovschii.game_library.entities;

import lombok.Data;

import java.util.List;

@Data
public class GenreDTO {

    private Genre genre;
    private List<Integer> genreGamesIds;
}
