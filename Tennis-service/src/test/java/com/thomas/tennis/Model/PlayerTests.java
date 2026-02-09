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

import com.thomas.tennis.Enums.Points;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation; 
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class PlayerTests {

    private String validName = "Jefrey";
    private Points validPoints = Points.LOVE;
    private int validGames = 0;
    private Player player;

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void setupValidator() {
        // This looks for the EL implementation and the Hibernate Validator on your classpath
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
        player = new Player(validName);
    }
    
    @Test
    public void givenValidInput_whenCreatingPlayer_thenPlayerIsCreatedWithThatInput() {
        assertNotNull(player);
        assertNotNull(player.getId());
        assertInstanceOf(Long.class,player.getId());
        assertEquals(validName, player.getName());
        assertEquals(validPoints, player.getPoints());
        assertEquals(validGames, player.getGames());
        Set<ConstraintViolation<Player>> violations = validator.validate(player);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void givenEmptyName_whenCreatingPlayerWithEmptyName_ThrowExceptionWithMessage(){
        // when
        String emptyName = "   ";
        Player playerWithNoName = new Player(emptyName);

        // then
        Set<ConstraintViolation<Player>> violations = validator.validate(playerWithNoName);
        assertEquals(1, violations.size());
        ConstraintViolation<Player> violation = violations.iterator().next();
        assertEquals("Name is required", violation.getMessage());
        assertEquals("name", violation.getPropertyPath().toString());
        assertEquals(emptyName, violation.getInvalidValue());
    }
}