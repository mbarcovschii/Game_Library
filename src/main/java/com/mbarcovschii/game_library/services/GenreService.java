package com.mbarcovschii.game_library.services;

import com.mbarcovschii.game_library.exceptions.genre.ExistingGenreException;
import com.mbarcovschii.game_library.exceptions.genre.GenreNotFoundException;
import com.mbarcovschii.game_library.model.Game;
import com.mbarcovschii.game_library.model.Genre;
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

    public Genre createGenre(Genre genre) {

        String genreName = genre.getGenreName();
        if (genreRepository.findByGenreName(genreName).isPresent()) {
            throw new ExistingGenreException(genreName);
        }

        if (genre.getGenreGames() == null) {
            genre.setGenreGames(new ArrayList<>());
        }

        return genreRepository.save(genre);
    }

    public Genre createGenre(Genre newGenre, List<Long> gameIds) {

        newGenre.setGenreGames(new ArrayList<>());

        if (gameIds != null) {
            List<Game> genreGames = new ArrayList<>();
            for (Long gameId : gameIds) {
                Game gameToAdd = gameService.getGameById(gameId);
                gameToAdd.getGameGenres().add(newGenre);
                genreGames.add(gameToAdd);
            }
            newGenre.setGenreGames(genreGames);
        }

        return createGenre(newGenre);
    }

    public Genre updateGenreName(Long genreId, String genreName) {

        Genre genre = getGenreById(genreId);
        if (genreRepository.findByGenreName(genreName).isPresent() && !genre.getGenreName().equals(genreName)) {
            throw new ExistingGenreException(genreName);
        }
        genre.setGenreName(genreName);

        return genreRepository.save(genre);
    }

    public Genre addNewGenreGames(Long genreId, List<Long> newGenreGamesIds) {

        Genre genre = getGenreById(genreId);
        List<Game> genreGames = genre.getGenreGames();
        for (Long gameId : newGenreGamesIds) {
            Game game = gameService.getGameById(gameId);
            List<Genre> gameGenres = game.getGameGenres();

            if (!genreGames.contains(game)) {
                genreGames.add(game);
            }
            if (!gameGenres.contains(genre)) {
                gameGenres.add(genre);
            }
        }

        return genreRepository.save(genre);
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

    public void deleteGenreGames(Long genreId, List<Long> gameIds) {

        Genre genreToUpdate = getGenreById(genreId);

        for (Long gameId : gameIds) {
            Game gameToRemoveFromList = gameService.getGameById(gameId);
            gameToRemoveFromList.getGameGenres().remove(genreToUpdate);
            genreToUpdate.getGenreGames().remove(gameToRemoveFromList);
        }

        genreRepository.save(genreToUpdate);
    }
}
