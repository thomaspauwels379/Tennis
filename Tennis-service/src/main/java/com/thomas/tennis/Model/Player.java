package com.thomas.tennis.Model;

import com.thomas.tennis.Enums.Points;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Entity
@Getter
@Table(name = "players")
public class Player {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    @NotBlank(message="Name is required")
    private String name;

    private Points points;

    private int games;

    @OneToOne
    @JoinColumn(name = "matchId", referencedColumnName = "id")
    private long matchId;

    protected Player(){}

    public Player(String name){
        this.name = name;
        this.points = Points.LOVE;
        this.games = 0;
    }

}
