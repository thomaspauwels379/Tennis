package com.thomas.tennis.Model;

import com.thomas.tennis.Enums.Points;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "players")
public class Player {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    private String name;

    private Points points;

    private int games;

    protected Player(){}

    public Player(String name){
        this.name = name;
        this.points = Points.LOVE;
        this.games = 0;
    }

}
