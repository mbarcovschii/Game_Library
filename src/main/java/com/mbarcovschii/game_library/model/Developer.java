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
    private List<Game> developerGames;
}
