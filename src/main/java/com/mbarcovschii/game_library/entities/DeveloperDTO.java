package com.mbarcovschii.game_library.entities;

import lombok.Data;

import java.util.List;

@Data
public class DeveloperDTO {

    private Developer developer;
    private List<Integer> developedGamesIds;
}
