package com.thomas.tennis.Model;

import com.thomas.tennis.Enums.MatchState;
import com.thomas.tennis.Enums.Points;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public Match(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        this.state = MatchState.STARTED;
    }

    public String getScore() {
        if (player1.getPoints() == player2.getPoints()) {
            if (player1.getPoints() == Points.FORTY) {
                return "DEUCE";
            }
            return renderPoint(player1.getPoints()) + " - ALL";
        }
        return renderPoint(player1.getPoints()) + " - " + renderPoint(player2.getPoints());
    }

    private String renderPoint(Points points) {
        return switch (points) {
            case LOVE    -> "LOVE";
            case FIFTEEN -> "15";
            case THIRTY  -> "30";
            case FORTY   -> "40";
            case ADV     -> "ADV";
        };
    }

    public void scorePoint(long playerId) throws Exception {
    if (this.player1.getId() == playerId) {
        this.player1.addPoint(this.player2);
    } else if (this.player2.getId() == playerId) {
        this.player2.addPoint(this.player1);
    } else {
        throw new Exception("Player ID " + playerId + " is geen onderdeel van deze match.");
    }
}
}
