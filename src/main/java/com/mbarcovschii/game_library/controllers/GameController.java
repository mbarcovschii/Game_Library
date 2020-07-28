package com.mbarcovschii.game_library.controllers;

import com.mbarcovschii.game_library.controllers.assemblers.GameModelAssembler;
import com.mbarcovschii.game_library.exceptions.PartialUpdateIsEmptyException;
import com.mbarcovschii.game_library.exceptions.RequiredFieldValueIsNotDefinedException;
import com.mbarcovschii.game_library.model.Game;
import com.mbarcovschii.game_library.model.dto.GameDTO;
import com.mbarcovschii.game_library.model.dto.GamePartialUpdate;
import com.mbarcovschii.game_library.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class GameController {

    private GameService gameService;
    private GameModelAssembler assembler;

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @Autowired
    public void setAssembler(GameModelAssembler assembler) {
        this.assembler = assembler;
    }

    @GetMapping("/games")
    public CollectionModel<EntityModel<Game>> getAllGames() {

        return CollectionModel.of(gameService.getAllGames().stream().
                        map(assembler::toModel).
                        collect(Collectors.toList()),
                linkTo(methodOn(GameController.class).getAllGames()).withSelfRel());
    }

    @PostMapping("/games")
    public ResponseEntity<EntityModel<Game>> postOneGame(@RequestBody GameDTO newGameDTO) {

        if (newGameDTO.getDeveloperId() == null) {
            throw new RequiredFieldValueIsNotDefinedException("developerId");
        }

        EntityModel<Game> entityModel = assembler.toModel(
                gameService.createGame(newGameDTO.getGame(),
                        newGameDTO.getDeveloperId(), newGameDTO.getGenreIds())
        );

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                body(entityModel);
    }

    @GetMapping("/games/{gameId}")
    public EntityModel<Game> getOneGameById(@PathVariable Long gameId) {

        return assembler.toModel(gameService.getGameById(gameId));
    }

    @PatchMapping("/games/{gameId}")
    public EntityModel<Game> patchOneGameById(@PathVariable Long gameId,
                                              @RequestBody GamePartialUpdate update) {

        if (update.isEmpty()) {
            throw new PartialUpdateIsEmptyException("Game partial update did not specify any fields to update");
        }

        if (update.getGameName() != null) {
            gameService.updateGameName(gameId, update.getGameName());
        }
        if (update.getDeveloperId() != null) {
            gameService.updateGameDeveloper(gameId, update.getDeveloperId());
        }
        if (update.getGenreIds() != null) {
            gameService.addNewGameGenres(gameId, update.getGenreIds());
        }

        return assembler.toModel(gameService.getGameById(gameId));
    }

    @DeleteMapping("/games/{gameId}")
    public ResponseEntity<?> deleteOneGameById(@PathVariable Long gameId,
                                               @RequestParam(required = false) List<Long> genreIds) {

        if (genreIds == null) {
            gameService.deleteGameById(gameId);
        } else {
            gameService.deleteGameGenres(gameId, genreIds);
        }

        return ResponseEntity.noContent().build();
    }
}
