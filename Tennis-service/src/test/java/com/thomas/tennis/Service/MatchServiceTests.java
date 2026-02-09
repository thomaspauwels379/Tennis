package com.thomas.tennis.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.security.Principal;

import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thomas.tennis.Model.Match;
import com.thomas.tennis.Model.Player;
import com.thomas.tennis.Repo.MatchRepository;

@ExtendWith(MockitoExtension.class)
public class MatchServiceTests {

    @Mock
    MatchRepository bookRepository;

    @InjectMocks
    MatchService matchService;

    
    private Player player1 = new Player("Jefrey");
    private Player player2 = new Player("Rick");
    private Player player3 = new Player("Tom");
    private Player player4 = new Player("Rick");
    private Match match1 = new Match(player1,player2);
    private Match match2 = new Match(player1,player2);

    @Test
    public void givenBooksWithBookWithTitleDonQuichot_whenGetBookWithTitleDonQuichot_thenBookIsReturned()  throws ServiceException {
        // given
        when (MatchRepository.findMatchById(match1.getId())).thenReturn(match1);
        // when
        Match foundMatch = matchService.getMatchById(match1.getId());
        // then
        assertEquals(foundMatch.getId(), match1.getId());
    }

}