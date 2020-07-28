package com.mbarcovschii.game_library.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class DeveloperPartialUpdate {

    private String developerName;
    private List<Long> gameIds;
}
