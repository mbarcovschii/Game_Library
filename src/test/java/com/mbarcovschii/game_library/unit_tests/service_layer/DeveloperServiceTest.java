package com.mbarcovschii.game_library.unit_tests.service_layer;

import com.mbarcovschii.game_library.exceptions.developer.DeveloperNotFoundException;
import com.mbarcovschii.game_library.model.Developer;
import com.mbarcovschii.game_library.model.Game;
import com.mbarcovschii.game_library.repositories.DeveloperRepository;
import com.mbarcovschii.game_library.services.DeveloperService;
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
public class DeveloperServiceTest {

    @Mock
    private DeveloperRepository developerRepository;

    @InjectMocks
    private DeveloperService developerService;

    private Developer developer;

    @BeforeEach
    void initData() {

        developer = new Developer(24L, "Blizzard", null);

        ArrayList<Game> developedGames = new ArrayList<>();
        developedGames.add(new Game(42L, "Starcraft II", developer, null));
        developedGames.add(new Game(42L, "Warcraft III", developer, null));
        developer.setDeveloperGames(developedGames);
    }

    @Test
    void shouldSaveDeveloperSuccessfully() {

        given(developerRepository.save(developer)).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Developer savedDeveloper = developerService.createDeveloper(developer);

        assertThat(savedDeveloper).isNotNull();
        verify(developerRepository).save(developer);
    }

    @Test
    void shouldFindDeveloperById() {

        Long developerId = developer.getDeveloperId();

        given(developerRepository.findById(developerId)).willReturn(Optional.of(developer));

        Developer expected = developerService.getDeveloperById(developerId);

        assertThat(expected).isNotNull();
        verify(developerRepository).findById(developerId);
    }

    @Test
    void shouldThrowExceptionWhenSearchingForNonexistentId() {

        Long wrongDeveloperId = developer.getDeveloperId() + 1;

        given(developerRepository.findById(wrongDeveloperId)).willReturn(Optional.empty());

        assertThatExceptionOfType(DeveloperNotFoundException.class).
                isThrownBy(() -> developerService.getDeveloperById(wrongDeveloperId));

        verify(developerRepository).findById(wrongDeveloperId);
    }
}
