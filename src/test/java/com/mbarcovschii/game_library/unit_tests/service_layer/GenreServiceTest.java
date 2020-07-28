package com.mbarcovschii.game_library.unit_tests.service_layer;

import com.mbarcovschii.game_library.exceptions.genre.ExistingGenreException;
import com.mbarcovschii.game_library.exceptions.genre.GenreNotFoundException;
import com.mbarcovschii.game_library.model.Game;
import com.mbarcovschii.game_library.model.Genre;
import com.mbarcovschii.game_library.repositories.GenreRepository;
import com.mbarcovschii.game_library.services.GameService;
import com.mbarcovschii.game_library.services.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private GameService gameService;

    @InjectMocks
    private GenreService genreService;

    private Genre genre;

    @BeforeEach
    void initData() {

        genre = new Genre(3L, "Real-Time Strategy", null);

        ArrayList<Game> genreGames = new ArrayList<>();
        genreGames.add(new Game(42L, "Starcraft II", null, null));
        genre.setGenreGames(genreGames);
        genreGames.add(new Game(43L, "Warcraft III", null, null));
        genre.setGenreGames(genreGames);

        ArrayList<Genre> gameGenres = new ArrayList<>();
        gameGenres.add(genre);
        genre.getGenreGames().get(0).setGameGenres(gameGenres);

        gameGenres = new ArrayList<>();
        gameGenres.add(genre);
        genre.getGenreGames().get(1).setGameGenres(gameGenres);
    }

    @Test
    void shouldSaveGenreSuccessfully() {

        given(genreRepository.findByGenreName(genre.getGenreName())).willReturn(Optional.empty());
        given(genreRepository.save(genre)).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Genre savedGenre = genreService.createGenre(genre);

        assertThat(savedGenre).isNotNull();
        verify(genreRepository).findByGenreName(genre.getGenreName());
        verify(genreRepository).save(genre);
    }

    @Test
    void shouldThrowExceptionWhenSaveGenreWithExistingName() {

        given(genreRepository.findByGenreName(genre.getGenreName())).willReturn(Optional.of(
                new Genre(1L, genre.getGenreName(), null)));

        assertThatExceptionOfType(ExistingGenreException.class).
                isThrownBy(() -> genreService.createGenre(genre));
        verify(genreRepository).findByGenreName(genre.getGenreName());
        verify(genreRepository, never()).save(genre);
    }

    @Test
    void shouldFindGenreById() {

        Long genreId = genre.getGenreId();

        given(genreRepository.findById(genreId)).willReturn(Optional.of(genre));

        Genre expected = genreService.getGenreById(genreId);

        assertThat(expected).isNotNull();
        verify(genreRepository).findById(genreId);
    }

    @Test
    void shouldThrowExceptionWhenSearchingForNonexistentId() {

        Long wrongGenreId = genre.getGenreId() + 1;

        given(genreRepository.findById(wrongGenreId)).willReturn(Optional.empty());

        assertThatExceptionOfType(GenreNotFoundException.class).
                isThrownBy(() -> genreService.getGenreById(wrongGenreId));

        verify(genreRepository).findById(wrongGenreId);
    }

    @Test
    void shouldRemoveGenreFromGenreListBeforeDeleting() {

        Long genreId = genre.getGenreId();
        List<Game> gameList = genre.getGenreGames();

        assertThat(genre.getGenreGames().size()).isEqualTo(2);
        assertThat(gameList.get(0).getGameGenres().size()).isEqualTo(1);
        assertThat(gameList.get(1).getGameGenres().size()).isEqualTo(1);

        given(genreRepository.findById(genreId)).willReturn(Optional.of(genre));

        genreService.deleteGenreById(genreId);
        assertThat(gameList.get(0).getGameGenres().size()).isEqualTo(0);
        assertThat(gameList.get(1).getGameGenres().size()).isEqualTo(0);

        verify(genreRepository).findById(genreId);
        verify(genreRepository).deleteById(genreId);
    }
}