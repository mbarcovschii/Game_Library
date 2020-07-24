package com.mbarcovschii.game_library.controllers;

import com.mbarcovschii.game_library.entities.Game;
import com.mbarcovschii.game_library.entities.GameDTO;
import com.mbarcovschii.game_library.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameController {

    private GameService service;

    @Autowired
    public void setGameService(GameService gameService) {
        this.service = gameService;
    }

    @GetMapping("/games")
    List<Game> getAllGames() {
        return service.getAllGames();
    }

    @PostMapping("/games")
    Game postOneGame(@RequestBody GameDTO newGameDTO) {
        return service.createGame(newGameDTO.getGame(),
                newGameDTO.getGameDeveloperId(), newGameDTO.getGameGenresIds());
    }

    @GetMapping("/games/{gameId}")
    Game getOneGameById(@PathVariable Long gameId) {
        return service.getGameById(gameId);
    }

    @DeleteMapping("/games/{gameId}")
    void deleteOneGameById(@PathVariable Long gameId) {
        service.deleteGameById(gameId);
    }
}
