package com.thomas.tennis.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.thomas.tennis.Enums.MatchState;
import com.thomas.tennis.Enums.Points;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation; 
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class MatchTests {

    private String validName1 = "Jefrey";
    private String validName2 = "Rick";
    private MatchState matchState = MatchState.STARTED;


    private Player player1;
    private Player player2;

    private Match match;

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void setupValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void tearDown() {
        if (validatorFactory != null) {
            validatorFactory.close();
        }
    }

    @BeforeEach
    public void setUp() {
        player1 = new Player(validName1);
        player2 = new Player(validName2);
        match = new Match(player1,player2);
    }
    
    @Test
    public void givenValidPlayersInInput_whenCreatingAnMatch_thenMatchIsCreatedWithThatInput() {
        assertNotNull(match);
        assertNotNull(match.getId());
        assertInstanceOf(Long.class,match.getId());
        assertEquals(player1.getId(), match.getPlayer1().getId());
        assertEquals(player2.getId(), match.getPlayer2().getId());
        assertEquals(matchState, match.getState());
        Set<ConstraintViolation<Player>> violations = validator.validate(match);
        assertTrue(violations.isEmpty());
    }
}