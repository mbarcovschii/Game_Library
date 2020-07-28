package com.mbarcovschii.game_library.services;

import com.mbarcovschii.game_library.exceptions.game.GameNotFoundException;
import com.mbarcovschii.game_library.model.Game;
import com.mbarcovschii.game_library.model.Genre;
import com.mbarcovschii.game_library.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    private GameRepository gameRepository;
    private GenreService genreService;
    private DeveloperService developerService;

    @Autowired
    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    @Autowired
    public void setDeveloperService(DeveloperService developerService) {
        this.developerService = developerService;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game getGameById(Long gameId) {
        return gameRepository.findById(gameId).
                orElseThrow(() -> new GameNotFoundException(gameId));
    }

    public Game createGame(Game game) {

        if (game.getGameGenres() == null) {
            game.setGameGenres(new ArrayList<>());
        }

        return gameRepository.save(game);
    }

    public Game createGame(Game game, Long gameDeveloperId, List<Long> gameGenreIds) {

        game.setGameDeveloper(null);
        game.setGameGenres(new ArrayList<>());

        if (gameDeveloperId != null) {
            game.setGameDeveloper(developerService.getDeveloperById(gameDeveloperId));
        }

        if (gameGenreIds != null) {
            List<Genre> gameGenres = new ArrayList<>();
            for (Long genreId : gameGenreIds) {
                Genre genreToAdd = genreService.getGenreById(genreId);
                genreToAdd.getGenreGames().add(game);
                gameGenres.add(genreToAdd);
            }
            game.setGameGenres(gameGenres);
        }

        return createGame(game);
    }

    public Game updateGameName(Long gameId, String gameName) {

        Game game = getGameById(gameId);
        game.setGameName(gameName);

        return gameRepository.save(game);
    }

    public Game updateGameDeveloper(Long gameId, Long developerId) {

        Game game = getGameById(gameId);
        game.setGameDeveloper(developerService.getDeveloperById(developerId));

        return gameRepository.save(game);
    }

    public Game addNewGameGenres(Long gameId, List<Long> genreIds) {

        Game game = getGameById(gameId);
        List<Genre> gameGenres = game.getGameGenres();
        for (long genreId : genreIds) {
            Genre genre = genreService.getGenreById(genreId);
            List<Game> genreGames = genre.getGenreGames();

            if (!gameGenres.contains(genre)) {
                gameGenres.add(genre);
            }
            if (!genreGames.contains(game)) {
                genreGames.add(game);
            }
        }

        return gameRepository.save(game);
    }

    public void deleteGameById(Long gameId) {

        Game gameToDelete = getGameById(gameId);

        if (gameToDelete.getGameDeveloper() != null) {
            gameToDelete.getGameDeveloper().getDeveloperGames().remove(gameToDelete);
        }

        if (gameToDelete.getGameGenres() != null) {
            for (Genre genre : gameToDelete.getGameGenres()) {
                genre.getGenreGames().remove(gameToDelete);
            }
        }

        gameRepository.deleteById(gameId);
    }

    public void deleteGameGenres(Long gameId, List<Long> genreIds) {

        Game gameToUpdate = getGameById(gameId);

        for (long genreId : genreIds) {
            Genre genreToRemoveFromList = genreService.getGenreById(genreId);
            genreToRemoveFromList.getGenreGames().remove(gameToUpdate);
            gameToUpdate.getGameGenres().remove(genreToRemoveFromList);
        }

        gameRepository.save(gameToUpdate);
    }
}
