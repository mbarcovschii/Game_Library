package com.mbarcovschii.game_library.controllers;

import com.mbarcovschii.game_library.controllers.assemblers.GenreModelAssembler;
import com.mbarcovschii.game_library.model.Genre;
import com.mbarcovschii.game_library.model.dto.GenreDTO;
import com.mbarcovschii.game_library.model.dto.GenrePartialUpdate;
import com.mbarcovschii.game_library.services.GenreService;
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
public class GenreController {

    private GenreService genreService;
    private GenreModelAssembler assembler;

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    @Autowired
    public void setGenreModelAssembler(GenreModelAssembler assembler) {
        this.assembler = assembler;
    }

    @GetMapping("/genres")
    public CollectionModel<EntityModel<Genre>> getAllGenres() {

        return CollectionModel.of(genreService.getAllGenres().stream().
                        map(assembler::toModel).
                        collect(Collectors.toList()),
                linkTo(methodOn(GenreController.class).getAllGenres()).withSelfRel());
    }

    @PostMapping("/genres")
    public ResponseEntity<EntityModel<Genre>> postOneGenre(@RequestBody GenreDTO newGenreDTO) {

        EntityModel<Genre> entityModel = assembler.toModel(
                genreService.createGenre(newGenreDTO.getGenre(),
                        newGenreDTO.getGameIds())
        );

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                body(entityModel);
    }

    @GetMapping("/genres/{genreId}")
    public EntityModel<Genre> getOneGenreById(@PathVariable Long genreId) {
        return assembler.toModel(genreService.getGenreById(genreId));
    }

    @PatchMapping("/genres/{genreId}")
    public EntityModel<Genre> patchOneGenreById(@PathVariable Long genreId,
                                                @RequestBody GenrePartialUpdate genrePartialUpdate) {

        genreService.updateGenreName(genreId, genrePartialUpdate.getGenreName());
        return assembler.toModel(genreService.addNewGenreGames(genreId, genrePartialUpdate.getGameIds()));
    }

    @DeleteMapping("/genres/{genreId}")
    public ResponseEntity<?> deleteOneGenreById(@PathVariable Long genreId,
                                                @RequestParam(required = false) List<Long> gameIds) {
        if (gameIds == null) {
            genreService.deleteGenreById(genreId);
        } else {
            genreService.deleteGenreGames(genreId, gameIds);
        }

        return ResponseEntity.noContent().build();
    }
}
