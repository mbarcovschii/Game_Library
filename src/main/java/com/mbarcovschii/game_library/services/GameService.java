package com.mbarcovschii.game_library.services;

import com.mbarcovschii.game_library.entities.Game;
import com.mbarcovschii.game_library.entities.Genre;
import com.mbarcovschii.game_library.exceptions.GameNotFoundException;
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

    public Game createGame(Game newGame) {
        return gameRepository.save(newGame);
    }

    public Game createGame(Game newGame, Integer gameDeveloperId, List<Integer> gameGenresIds) {

        newGame.setGameDeveloper(null);
        newGame.setGameGenres(new ArrayList<>());

        if (gameDeveloperId != null) {
            newGame.setGameDeveloper(developerService.getDeveloperById(Long.valueOf(gameDeveloperId)));
        }

        if (gameGenresIds != null) {
            List<Genre> gameGenres = new ArrayList<>();
            for (long genreId : gameGenresIds) {
                Genre genreToAdd = genreService.getGenreById(genreId);
                genreToAdd.getGenreGames().add(newGame);
                gameGenres.add(genreToAdd);
            }
            newGame.setGameGenres(gameGenres);
        }

        return createGame(newGame);
    }

    public void deleteGameById(Long gameId) {

        Game gameToDelete = getGameById(gameId);

        if (gameToDelete.getGameDeveloper() != null) {
            gameToDelete.getGameDeveloper().getDevelopedGames().remove(gameToDelete);
        }

        if (gameToDelete.getGameGenres() != null) {
            for (Genre genre : gameToDelete.getGameGenres()) {
                genre.getGenreGames().remove(gameToDelete);
            }
        }

        gameRepository.deleteById(gameId);
    }
}
