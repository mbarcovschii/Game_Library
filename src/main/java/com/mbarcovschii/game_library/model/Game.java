package com.mbarcovschii.game_library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    @JsonIgnoreProperties("developerGames")
    private Developer gameDeveloper;

    // Owner
    @ManyToMany
    @JoinTable(name = "game_genre",
            joinColumns = {@JoinColumn(name = "game_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    @JsonIgnoreProperties("genreGames")
    private List<Genre> gameGenres;
}
