package com.mbarcovschii.game_library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* TODO
    Find out why @JsonIgnoreProperties isn't working with Set, but only with List (Many to Many genre_game)
    Add HATEOAS
    Try to replace all Lists with Sets
    Add tests for Service Layer creating methods
    Write integration test for Web Layer
 */

@SpringBootApplication
public class GameLibraryApplication {

    public static void main(String[] args) {

        SpringApplication.run(GameLibraryApplication.class, args);
    }

}
