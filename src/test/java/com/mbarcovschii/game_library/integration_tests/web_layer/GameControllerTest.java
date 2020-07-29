package com.mbarcovschii.game_library.integration_tests.web_layer;

import com.mbarcovschii.game_library.controllers.GameController;
import com.mbarcovschii.game_library.controllers.assemblers.GameModelAssembler;
import com.mbarcovschii.game_library.model.Game;
import com.mbarcovschii.game_library.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private GameModelAssembler assembler;

    private List<Game> gameList;

    @BeforeEach
    void initData() {

        gameList = new ArrayList<>();
        Game game = new Game(1L, "Starcraft I", null, null);
        gameList.add(game);
        game = new Game(2L, "Starcraft II", null, null);
        gameList.add(game);
    }

    @Test
    void shouldGetGameByIdSuccessfully() throws Exception {

        Game game = gameList.get(0);

        given(gameService.getGameById(game.getGameId())).willReturn(game);
        given(assembler.toModel(game)).willReturn(EntityModel.of(game));

        mockMvc.perform(get("/games/" + game.getGameId()).
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$", aMapWithSize(4))).
                andExpect(jsonPath("$.gameName", is("Starcraft I")));
    }
}
