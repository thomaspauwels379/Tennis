package com.thomas.tennis.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class PlayerTests {

    private long validId = 1;
    private String validName = "Jefrey";
    private int validScore = 0;
    private int validGames = 0;

    // constructor happy cases
    @Test
    public void givenValidInput_whenCreatingPlayer_thenPlayerIsCreatedWithThatInput() {
        Player player = Player(validName,validScore,validGames);
        assertNotNull(player);
        assertEquals(validId, player.getId());
        assertEquals(validName, player.getName());
        assertEquals(validScore, player.getScore());
        assertEquals(validGames, player.getGames());
    }
}