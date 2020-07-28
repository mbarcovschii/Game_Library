package com.mbarcovschii.game_library.model.dto;

import com.mbarcovschii.game_library.model.Genre;
import lombok.Data;

import java.util.List;

@Data
public class GenreDTO {

    private Genre genre;
    private List<Long> gameIds;
}
