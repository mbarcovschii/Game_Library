package com.mbarcovschii.game_library.model.dto;

import com.mbarcovschii.game_library.model.Game;
import lombok.Data;

import java.util.List;

@Data
public class GameDTO {

    private Game game;
    private Long developerId;
    private List<Long> genreIds;
}
