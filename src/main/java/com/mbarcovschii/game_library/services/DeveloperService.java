package com.mbarcovschii.game_library.services;

import com.mbarcovschii.game_library.entities.Developer;
import com.mbarcovschii.game_library.entities.Game;
import com.mbarcovschii.game_library.exceptions.DeveloperNotFoundException;
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

    public Developer createDeveloper(Developer newDeveloper) {
        return developerRepository.save(newDeveloper);
    }

    public Developer createDeveloper(Developer newDeveloper, List<Integer> developedGamesIds) {

        newDeveloper.setDevelopedGames(new ArrayList<>());

        if (developedGamesIds != null) {
            List<Game> developedGames = new ArrayList<>();
            for (long gameId : developedGamesIds) {
                Game gameToAdd = gameService.getGameById(gameId);
                gameToAdd.setGameDeveloper(newDeveloper);
                developedGames.add(gameToAdd);
            }
            newDeveloper.setDevelopedGames(developedGames);
        }

        return createDeveloper(newDeveloper);
    }

    public void deleteDeveloperById(Long developerId) {

        Developer developerToDelete = getDeveloperById(developerId);

        if (developerToDelete.getDevelopedGames() != null) {
            for (Game game : developerToDelete.getDevelopedGames()) {
                game.setGameDeveloper(null);
            }
        }

        developerRepository.deleteById(developerId);
    }
}
