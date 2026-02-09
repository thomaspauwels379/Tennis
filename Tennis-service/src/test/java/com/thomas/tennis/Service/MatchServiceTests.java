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
import org.junit.jupiter.api.BeforeEach;
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

    private Player player1;
    private Player player2;
    private Match match1;

    @BeforeEach
    public void setup() {
        player1 = new Player("Jefrey");
        player2 = new Player("Rick");
        match1 = new Match(player1, player2);
    }

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

    @Test
    public void givenValidMatch_whenPlayer1ScoresTwice_thenScoreIsThirtyLove() {
        // GIVEN
        when(matchRepository.findById(any(Long.class))).thenReturn(Optional.of(match1));
        when(matchRepository.save(any(Match.class))).thenAnswer(i -> i.getArguments()[0]);

        // WHEN - Player 1 scoort twee keer (0 -> 15 -> 30)
        matchService.addPointToMatch(match1.getId(), player1.getId());
        matchService.addPointToMatch(match1.getId(), player1.getId());

        // THEN
        assertEquals(com.thomas.tennis.Enums.Points.THIRTY, player1.getPoints(), "Player 1 moet op 30 staan");
        assertEquals(com.thomas.tennis.Enums.Points.LOVE, player2.getPoints(), "Player 2 moet op LOVE blijven staan");
    }

    @Test
    public void givenDeuce_whenPlayer1Scores_thenPlayer1HasAdvantage() {
        // GIVEN
        player1.setPoints(com.thomas.tennis.Enums.Points.FORTY);
        player2.setPoints(com.thomas.tennis.Enums.Points.FORTY);
        
        when(matchRepository.findById(any(Long.class))).thenReturn(Optional.of(match1));
        when(matchRepository.save(any(Match.class))).thenAnswer(i -> i.getArguments()[0]);

        // WHEN
        matchService.addPointToMatch(match1.getId(), player1.getId());

        // THEN
        assertEquals(com.thomas.tennis.Enums.Points.ADV, player1.getPoints(), "Player 1 moet Advantage hebben");
        assertEquals(com.thomas.tennis.Enums.Points.FORTY, player2.getPoints());
    }

}