package com.mbarcovschii.game_library.repositories;

import com.mbarcovschii.game_library.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

}
