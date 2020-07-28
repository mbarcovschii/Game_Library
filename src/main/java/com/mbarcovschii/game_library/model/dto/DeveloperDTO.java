package com.mbarcovschii.game_library.model.dto;

import com.mbarcovschii.game_library.model.Developer;
import lombok.Data;

import java.util.List;

@Data
public class DeveloperDTO {

    private Developer developer;
    private List<Long> gameIds;
}
