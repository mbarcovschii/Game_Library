package com.mbarcovschii.game_library.controllers;

import com.mbarcovschii.game_library.entities.Genre;
import com.mbarcovschii.game_library.entities.GenreDTO;
import com.mbarcovschii.game_library.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GenreController {

    private GenreService service;

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.service = genreService;
    }

    @GetMapping("/genres")
    List<Genre> getAllGenres() {
        return service.getAllGenres();
    }

    @PostMapping("/genres")
    Genre postOneGenre(@RequestBody GenreDTO newGenreDTO) {
        return service.createGenre(newGenreDTO.getGenre(), newGenreDTO.getGenreGamesIds());
    }

    @GetMapping("/genres/{genreId}")
    Genre getOneGenreById(@PathVariable Long genreId) {
        return service.getGenreById(genreId);
    }

    @DeleteMapping("/genres/{genreId}")
    void deleteOneGenreById(@PathVariable Long genreId) {
        service.deleteGenreById(genreId);
    }
}
