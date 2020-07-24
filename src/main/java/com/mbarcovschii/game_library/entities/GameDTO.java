package com.mbarcovschii.game_library.entities;

import lombok.Data;

import java.util.List;

@Data
public class GameDTO {

    private Game game;
    private Integer gameDeveloperId;
    private List<Integer> gameGenresIds;
}
