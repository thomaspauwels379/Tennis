package com.thomas.tennis.Model;

import com.thomas.tennis.Enums.MatchState;
import com.thomas.tennis.Enums.Points;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player1_id", referencedColumnName = "id")
    private Player player1;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player2_id", referencedColumnName = "id")
    private Player player2;

    @Enumerated(EnumType.ORDINAL)
    private MatchState state;

    public Match(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        this.state = MatchState.ONGOING;
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
        Player player; 
        if (this.player1.getId() == playerId) {
            player = this.player1;
        } else if (this.player2.getId() == playerId) {
            player = this.player2;
        } else {
            throw new Exception("Player ID " + playerId + " is geen onderdeel van deze match.");
        }
        player.addPoint(this.player1);
        if(player.getGames() == 3){
            setState(MatchState.FINISHED);
        }
    }

    public void cancelMatch() {
        setState(MatchState.CANCELED);
    }

}