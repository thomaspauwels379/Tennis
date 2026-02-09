package com.thomas.tennis.Model;

import com.thomas.tennis.Enums.MatchState;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class Match {

    
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    @OneToOne
    @JoinColumn(name = "player1", referencedColumnName = "id")
    private Player player1;

    @OneToOne
    @JoinColumn(name = "player2", referencedColumnName = "id")
    private Player player2;

    private MatchState state;

    protected Match(){}

    public Match(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        this.state = MatchState.STARTED;
    }
}
