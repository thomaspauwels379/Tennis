package com.thomas.tennis.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.thomas.tennis.Enums.Points;

public class PlayerTests {

    private String validName = "Jefrey";
    private Points validScore = Points.LOVE;
    private int validGames = 0;

    // constructor happy cases
    @Test
    public void givenValidInput_whenCreatingPlayer_thenPlayerIsCreatedWithThatInput() {
        Player player = new Player(validName);
        assertNotNull(player);
        assertNotNull(player.getId());
        assertInstanceOf(Long.class,player.getId());
        assertEquals(validName, player.getName());
        assertEquals(validScore, player.getScore());
        assertEquals(validGames, player.getGames());
    }
}