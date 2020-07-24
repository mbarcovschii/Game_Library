package com.mbarcovschii.game_library.services;

import com.mbarcovschii.game_library.entities.Game;
import com.mbarcovschii.game_library.entities.Genre;
import com.mbarcovschii.game_library.exceptions.ExistingGenreException;
import com.mbarcovschii.game_library.exceptions.GenreNotFoundException;
import com.mbarcovschii.game_library.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService {

    private GenreRepository genreRepository;
    private GameService gameService;

    @Autowired
    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreById(Long genreId) {

        return genreRepository.findById(genreId).
                orElseThrow(() -> new GenreNotFoundException(genreId));
    }

    public Genre createGenre(Genre newGenre) {

        String genreName = newGenre.getGenreName();
        if (genreRepository.findByGenreName(genreName).isPresent()) {
            throw new ExistingGenreException(genreName);
        }

        return genreRepository.save(newGenre);
    }

    public Genre createGenre(Genre newGenre, List<Integer> genreGamesIds) {

        newGenre.setGenreGames(new ArrayList<>());

        if (genreGamesIds != null) {
            List<Game> genreGames = new ArrayList<>();
            for (long gameId : genreGamesIds) {
                Game gameToAdd = gameService.getGameById(gameId);
                gameToAdd.getGameGenres().add(newGenre);
                genreGames.add(gameToAdd);
            }
            newGenre.setGenreGames(genreGames);
        }

        return createGenre(newGenre);
    }

    public void deleteGenreById(Long genreId) {

        Genre genreToDelete = getGenreById(genreId);
        List<Game> genreGames = genreToDelete.getGenreGames();

        if (genreGames != null) {
            for (Game game : genreGames) {
                game.getGameGenres().remove(genreToDelete);
            }
        }

        genreRepository.deleteById(genreId);
    }
}
