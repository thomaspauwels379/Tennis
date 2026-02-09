package com.thomas.tennis.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.thomas.tennis.Model.Match;
import com.thomas.tennis.Model.Player;
import com.thomas.tennis.Repo.MatchRepository;
import com.thomas.tennis.Repo.PlayerRepository;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTests {

    @Mock
    PlayerRepository playerRepository;

    @InjectMocks
    PlayerService playerService;
    
    private Player player1 = new Player("Jefrey");
    private Player player2 = new Player("Rick");

    @Test
    public void givenPlayerWithId1_whenGetPlayer1_thenPlayerIsReturned() {
        // given
        when (playerRepository.findById(player1.getId())).thenReturn(Optional.of(player1));
        // when
        Player foundPlayer = playerService.getPlayerById(player1.getId());
        // then
        assertEquals(foundPlayer.getId(), player1.getId());
    }

    @Test
    public void givenPlayerWithId0_whenGetPlayer1_thenReturnNull() {
        // when
        Player foundPlayer = playerService.getPlayerById(player1.getId());
        // then
        assertNull(foundPlayer);
    }

    @Test
    public void givenPlayerNames_whenPlayersAreMade_theyShouldBeReturnedWithTheRightName(){
        // given
        when(playerRepository.saveAll(anyIterable())).thenReturn(List.of(player1,player2));
        // when
        List<Player> players = playerService.createPlayers(player1.getName(), player2.getName());
        // then
        assertEquals(players.size(), 2);
        assertTrue(players.contains(player1));
        assertTrue(players.contains(player2));
    }
}