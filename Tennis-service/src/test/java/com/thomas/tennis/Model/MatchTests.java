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

import jakarta.persistence.Entity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation; 
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.Getter;

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
    public static void clear() {
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
        Set<ConstraintViolation<Match>> violations = validator.validate(match);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void givenAnOngoingMatchWithScore15To30_whenGettingTheMatchScore_thenMatchScoreIsReturned() {
        // given
        player1.setPoints(Points.FIFTEEN);
        player2.setPoints(Points.THIRTY);        
        // when
        String score = match.getScore();

        // then
        assertEquals("15 - 30", score);
    }

    @Test
    public void givenAnOngoingMatchWithScor30To30_whenGettingTheMatchScore_thenMatchScoreIsReturned() {
        // given
        player1.setPoints(Points.THIRTY);
        player2.setPoints(Points.THIRTY);        
        // when
        String score = match.getScore();

        // then
        assertEquals("30 - ALL", score);
    }

    @Test
    public void givenAnOngoingMatchAtFortyForty_whenGettingTheMatchScore_thenMatchScoreIsDeuce() {
        // given
        player1.setPoints(Points.FORTY);
        player2.setPoints(Points.FORTY);        
        // when
        String score = match.getScore();

        // then
        assertEquals("DEUCE", score);
    }


    @Test
    public void givenAnOngoingMatchWithAdvantagePlayer1_whenGettingTheMatchScore_thenMatchScoreIsAdvantagePlayer1() {
        // given
        player1.setPoints(Points.ADV);
        player2.setPoints(Points.FORTY);        
        // when
        String score = match.getScore();

        // then
        assertEquals("ADV - 40", score);
    }

}