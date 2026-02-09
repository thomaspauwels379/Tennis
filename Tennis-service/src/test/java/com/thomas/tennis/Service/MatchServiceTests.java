package com.thomas.tennis.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.security.Principal;
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

    @InjectMocks
    MatchService matchService;

    
    private Player player1 = new Player("Jefrey");
    private Player player2 = new Player("Rick");
    private Player player3 = new Player("Tom");
    private Player player4 = new Player("Rick");
    private Match match1 = new Match(player1,player2);
    private Match match2 = new Match(player1,player2);

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
}