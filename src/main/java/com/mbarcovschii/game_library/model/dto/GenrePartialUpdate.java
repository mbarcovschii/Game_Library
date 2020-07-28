package com.mbarcovschii.game_library.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class GenrePartialUpdate {

    String genreName;
    List<Long> gameIds;
}
