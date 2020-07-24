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
@Table(name = "developers")
public class Developer {

    @Id
    @Column(name = "developer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long developerId;

    @Column(name = "developer_name")
    private String developerName;

    @OneToMany(mappedBy = "gameDeveloper")
    @JsonIgnoreProperties("gameDeveloper")
    private List<Game> developedGames;

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Developer)) {
            return false;
        }
        Developer dev = (Developer) obj;
        return Objects.equals(this.getDeveloperId(), dev.getDeveloperId()) &&
                Objects.equals(this.getDeveloperName(), dev.getDeveloperName()) &&
                this.getDevelopedGames().equals(dev.getDevelopedGames());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getDeveloperId(), this.getDeveloperName(), this.getDevelopedGames());
    }

    @Override
    public String toString() {

        return String.format("Developer{id='%d', name='%s', developed_games='%s'}",
                this.getDeveloperId(), this.getDeveloperName(), this.getDevelopedGames().toString());
    }
}
