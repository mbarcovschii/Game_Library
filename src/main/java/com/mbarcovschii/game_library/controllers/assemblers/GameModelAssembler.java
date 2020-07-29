package com.mbarcovschii.game_library.controllers.assemblers;

import com.mbarcovschii.game_library.controllers.GameController;
import com.mbarcovschii.game_library.model.Game;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GameModelAssembler implements RepresentationModelAssembler<Game, EntityModel<Game>> {

    @Override
    public EntityModel<Game> toModel(Game game) {

        return EntityModel.of(game,
                linkTo(methodOn(GameController.class).getOneGameById(game.getGameId())).
                        withSelfRel(),
                linkTo(methodOn(GameController.class).getAllGames()).
                        withRel("games"),
                linkTo(methodOn(GameController.class).deleteOneGameById(game.getGameId(), null)).
                        withRel("delete").expand());
    }
}
