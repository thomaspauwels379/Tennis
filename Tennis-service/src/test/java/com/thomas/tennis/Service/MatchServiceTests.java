package com.thomas.tennis.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

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

@ExtendWith(MockitoExtension.class)
public class MatchServiceTests {

    @Mock
    MatchRepository matchRepository;

    @Mock
    PlayerService playerService;

    @InjectMocks
    MatchService matchService;

    
    private Player player1 = new Player("Jefrey");
    private Player player2 = new Player("Rick");
    private Match match1 = new Match(player1,player2);

    @Test
    public void givenMatchWithId1_whenGetMatch1_thenMatchIsReturned() {
        // given
        when (matchRepository.findById(match1.getId())).thenReturn(Optional.of(match1));
        // when
        Match foundMatch = matchService.getMatchById(match1.getId());
        // then
        assertEquals(foundMatch.getId(), match1.getId());
    }

    @Test
    public void givenMatchWithId0_whenGetMatch1_thenReturnNull() {
        // when
        Match foundMatch = matchService.getMatchById(match1.getId());
        // then
        assertNull(foundMatch);
    }

    @Test
    public void givenPlayers1And2_whenCreatingAMatch_thenReturnTheMatch() {
        // given
        when(playerService.createPlayers(player1.getName(), player2.getName())).thenReturn(List.of(player1,player2));
        when(matchRepository.save(any())).thenReturn(match1);
        // when
        Match createdMatch = matchService.createMatch(player1.getName(),player2.getName());
        // then
        assertNotNull(createdMatch);
        assertEquals(createdMatch.getPlayer1(),player1);
        assertEquals(createdMatch.getPlayer2(),player2);
    }

}