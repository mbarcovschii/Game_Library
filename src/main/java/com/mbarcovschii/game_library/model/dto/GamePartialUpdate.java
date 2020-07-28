package com.mbarcovschii.game_library.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class GamePartialUpdate {

    String gameName;
    Long developerId;
    List<Long> genreIds;

    public boolean isEmpty() {
        return gameName == null && developerId == null && genreIds == null;
    }
}
