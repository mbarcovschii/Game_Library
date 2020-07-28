package com.mbarcovschii.game_library.services;

import com.mbarcovschii.game_library.exceptions.developer.DeveloperNotFoundException;
import com.mbarcovschii.game_library.model.Developer;
import com.mbarcovschii.game_library.model.Game;
import com.mbarcovschii.game_library.repositories.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeveloperService {

    private DeveloperRepository developerRepository;
    private GameService gameService;

    @Autowired
    public void setDeveloperRepository(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    public List<Developer> getAllDevelopers() {
        return developerRepository.findAll();
    }

    public Developer getDeveloperById(Long developerId) {
        return developerRepository.findById(developerId).
                orElseThrow(() -> new DeveloperNotFoundException(developerId));
    }

    public Developer createDeveloper(Developer developer) {

        if (developer.getDeveloperGames() == null) {
            developer.setDeveloperGames(new ArrayList<>());
        }

        return developerRepository.save(developer);
    }

    public Developer createDeveloper(Developer developer, List<Long> developerGameIds) {

        developer.setDeveloperGames(new ArrayList<>());

        if (developerGameIds != null) {
            List<Game> developerGames = new ArrayList<>();
            for (Long gameId : developerGameIds) {
                Game gameToAdd = gameService.getGameById(gameId);
                gameToAdd.setGameDeveloper(developer);
                developerGames.add(gameToAdd);
            }
            developer.setDeveloperGames(developerGames);
        }

        return createDeveloper(developer);
    }

    public Developer updateDeveloper(Long developerId, String developerName) {

        Developer developer = getDeveloperById(developerId);
        developer.setDeveloperName(developerName);

        return developerRepository.save(developer);
    }

    public Developer addNewDeveloperGames(Long developerId, List<Long> gameIds) {

        Developer developer = getDeveloperById(developerId);
        for (Long gameId : gameIds) {
            Game game = gameService.getGameById(gameId);
            game.setGameDeveloper(developer);
            developer.getDeveloperGames().add(game);
        }

        return developerRepository.save(developer);
    }

    public void deleteDeveloperById(Long developerId) {

        Developer developerToDelete = getDeveloperById(developerId);

        if (developerToDelete.getDeveloperGames() != null) {
            for (Game game : developerToDelete.getDeveloperGames()) {
                game.setGameDeveloper(null);
            }
        }

        developerRepository.deleteById(developerId);
    }

    public void deleteDeveloperGames(Long developerId, List<Long> gameIds) {

        Developer developerToUpdate = getDeveloperById(developerId);

        for (Long gameId : gameIds) {
            Game gameToRemoveFromList = gameService.getGameById(gameId);
            developerToUpdate.getDeveloperGames().remove(gameToRemoveFromList);
            gameToRemoveFromList.setGameDeveloper(null);
        }

        developerRepository.save(developerToUpdate);
    }
}
