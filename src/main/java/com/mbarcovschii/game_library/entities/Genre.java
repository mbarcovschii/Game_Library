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

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Genre)) {
            return false;
        }
        Genre genre = (Genre) obj;
        return Objects.equals(this.getGenreId(), genre.getGenreId()) &&
                Objects.equals(this.getGenreName(), genre.getGenreName()) &&
                this.getGenreGames().equals(genre.getGenreGames());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getGenreId(), this.getGenreName(), this.getGenreGames());
    }

    @Override
    public String toString() {

        return String.format("Genre{id='%d', name='%s', games_of_this_genre='%s'}",
                this.getGenreId(), this.getGenreName(), this.getGenreGames().toString());
    }
}
