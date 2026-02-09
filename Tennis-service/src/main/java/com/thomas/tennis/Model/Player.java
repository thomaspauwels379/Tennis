package com.thomas.tennis.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "players")
public class Player {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    private String name;

    private int score;

    private int games;

    protected Player(){}

    public Player(String name){
        this.name = name;
        this.score = 0;
        this.games = 0;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getGames() {
        return games;
    }

}
