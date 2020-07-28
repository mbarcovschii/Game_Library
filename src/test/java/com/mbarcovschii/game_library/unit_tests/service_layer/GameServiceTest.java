package com.mbarcovschii.game_library.unit_tests.service_layer;

import com.mbarcovschii.game_library.exceptions.game.GameNotFoundException;
import com.mbarcovschii.game_library.model.Developer;
import com.mbarcovschii.game_library.model.Game;
import com.mbarcovschii.game_library.model.Genre;
import com.mbarcovschii.game_library.repositories.GameRepository;
import com.mbarcovschii.game_library.services.DeveloperService;
import com.mbarcovschii.game_library.services.GameService;
import com.mbarcovschii.game_library.services.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    GameRepository gameRepository;

    @Mock
    DeveloperService developerService;

    @Mock
    GenreService genreService;

    @InjectMocks
    GameService gameService;

    private Game game;

    @BeforeEach
    void initData() {

        Developer developer = new Developer(24L, "Blizzard", null);
        game = new Game(42L, "Starcraft II", developer, null);
        Genre genre = new Genre(3L, "Real-Time Strategy", null);

        ArrayList<Game> developedGames = new ArrayList<>();
        developedGames.add(game);
        developer.setDeveloperGames(developedGames);

        ArrayList<Genre> gameGenres = new ArrayList<>();
        gameGenres.add(genre);
        game.setGameGenres(gameGenres);

        ArrayList<Game> genreGames = new ArrayList<>();
        genreGames.add(game);
        genre.setGenreGames(genreGames);
    }

    @Test
    void shouldSaveGameSuccessfully() {

        given(gameRepository.save(game)).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Game savedGame = gameService.createGame(game);

        assertThat(savedGame).isNotNull();
        verify(gameRepository).save(game);
    }

    @Test
    void shouldFindGameById() {

        Long gameId = game.getGameId();

        given(gameRepository.findById(gameId)).willReturn(Optional.of(game));

        Game expected = gameService.getGameById(gameId);

        assertThat(expected).isNotNull();
        verify(gameRepository).findById(gameId);
    }

    @Test
    void shouldThrowExceptionWhenSearchingForNonexistentId() {

        Long wrongGameId = game.getGameId() + 1;

        given(gameRepository.findById(wrongGameId)).willReturn(Optional.empty());

        assertThatExceptionOfType(GameNotFoundException.class).
                isThrownBy(() -> gameService.getGameById(wrongGameId));

        verify(gameRepository).findById(wrongGameId);
    }

    @Test
    void shouldRemoveGameFromGameListsBeforeDeleting() {

        Long gameId = game.getGameId();
        Developer developer = game.getGameDeveloper();
        Genre genre = game.getGameGenres().get(0);

        assertThat(developer.getDeveloperGames().size()).isEqualTo(1);
        assertThat(genre.getGenreGames().size()).isEqualTo(1);

        given(gameRepository.findById(gameId)).willReturn(Optional.of(game));

        gameService.deleteGameById(gameId);
        assertThat(developer.getDeveloperGames().size()).isEqualTo(0);
        assertThat(genre.getGenreGames().size()).isEqualTo(0);

        verify(gameRepository).findById(gameId);
        verify(gameRepository).deleteById(gameId);
    }
}
