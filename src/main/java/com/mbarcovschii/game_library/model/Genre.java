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
@Table(name = "genres",
        uniqueConstraints = @UniqueConstraint(columnNames = {"genre_name"}))
public class Genre {

    @Id
    @Column(name = "genre_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreId;

    @Column(name = "genre_name")
    private String genreName;

    @ManyToMany(mappedBy = "gameGenres")
    @JsonIgnoreProperties("gameGenres")
    private List<Game> genreGames;
}
