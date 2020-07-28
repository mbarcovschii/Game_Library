package com.mbarcovschii.game_library.integration_tests.database_layer;

import com.mbarcovschii.game_library.model.Developer;
import com.mbarcovschii.game_library.model.Game;
import com.mbarcovschii.game_library.model.Genre;
import com.mbarcovschii.game_library.repositories.DeveloperRepository;
import com.mbarcovschii.game_library.repositories.GameRepository;
import com.mbarcovschii.game_library.repositories.GenreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UnnecessaryTests {

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private GameRepository gameRepository;

    @Test
    void injectedComponentsAreNotNull() {

        assertThat(developerRepository).isNotNull();
    }

    @Test
    void developerRepCrudTest() {

        Developer developer =
                new Developer(1L, "Blizzard", new ArrayList<>());

        assertThat(developerRepository.save(developer)).isEqualTo(developer);

        assertThat(developerRepository.findById(developer.getDeveloperId())).isNotEmpty();

        developerRepository.deleteById(developer.getDeveloperId());
        assertThat(developerRepository.findById(developer.getDeveloperId())).isEmpty();
    }

    @Test
    void genreRepCrudTest() {

        Genre genre =
                new Genre(1L, "Real-Time Strategy", new ArrayList<>());

        assertThat(genreRepository.save(genre)).isEqualTo(genre);

        assertThat(genreRepository.findById(genre.getGenreId())).isNotEmpty();
        assertThat(genreRepository.findByGenreName(genre.getGenreName())).isNotEmpty();

        genreRepository.deleteById(genre.getGenreId());
        assertThat(genreRepository.findById(genre.getGenreId())).isEmpty();
    }

    @Test
    void gameRepCrudTest() {

        Game game =
                new Game(1L, "Starcraft II", null, new ArrayList<>());

        assertThat(gameRepository.save(game)).isEqualTo(game);

        assertThat(gameRepository.findById(game.getGameId())).isNotEmpty();

        gameRepository.deleteById(game.getGameId());
        assertThat(gameRepository.findById(game.getGameId())).isEmpty();
    }
}
