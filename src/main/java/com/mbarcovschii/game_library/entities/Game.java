package com.mbarcovschii.game_library.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "game_name")
    private String gameName;

    @ManyToOne()
    @JoinColumn(name = "developer_id")
    @JsonIgnoreProperties("developedGames")
    private Developer gameDeveloper;

    @ManyToMany
    @JoinTable(name = "game_genre",
            joinColumns = {@JoinColumn(name = "game_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    @JsonIgnoreProperties("genreGames")
    private List<Genre> gameGenres;

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Game)) {
            return false;
        }
        Game game = (Game) obj;
        return Objects.equals(this.getGameId(), game.getGameId()) &&
                Objects.equals(this.getGameName(), game.getGameName()) &&
                Objects.equals(this.getGameDeveloper(), game.getGameDeveloper()) &&
                this.getGameGenres().equals(game.getGameGenres());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getGameId(), this.getGameName(),
                this.getGameDeveloper(), this.getGameGenres());
    }

    @Override
    public String toString() {

        return String.format("Game{id=%d, name='%s', developer='%s', genres='%s'}",
                this.getGameId(), this.getGameName(), this.getGameDeveloper(), this.getGameGenres().toString());
    }
}
