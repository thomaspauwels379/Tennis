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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @JoinColumn(name = "match", referencedColumnName = "id")
    private Match match;

    public Player(String name){
        this.name = name;
        this.points = Points.LOVE;
        this.games = 0;
    }

    public void addPoint(Player opponent){
        switch (this.points) {
        case ADV:
            this.winGame(opponent);
            break;

        case FORTY:
            if (opponent.getPoints() == Points.ADV) {
                opponent.setPoints(Points.FORTY);
            } else if (opponent.getPoints() == Points.FORTY) {
                this.points = Points.ADV;
            } else {
                this.winGame(opponent);
            }
            break;

        default:
            this.points = Points.values()[this.points.ordinal() + 1];
            break;
        }
    }

    
    private void winGame(Player opponent) {
        this.points = Points.LOVE;
        opponent.setPoints(Points.LOVE); // Vergeet de tegenstander niet!
        this.games++;
    }

}
