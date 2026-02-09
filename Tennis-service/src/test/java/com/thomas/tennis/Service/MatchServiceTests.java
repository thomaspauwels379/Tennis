package com.thomas.tennis.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import com.thomas.tennis.Enums.Points;
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
        player1.setId(1L);
        
        player2 = new Player("Rick");
        player2.setId(2L);

        match1 = new Match(player1, player2);
        match1.setId(100L);
    }

    @Test
    public void givenMatchWithId100_whenGetMatch100_thenMatchIsReturned() {
        // given
        when (matchRepository.findById(match1.getId())).thenReturn(Optional.of(match1));
        // when
        Match foundMatch = matchService.getMatchById(match1.getId());
        // then
        assertEquals(foundMatch.getId(), match1.getId());
    }

    @Test
    public void givenMatchWithId100_whenGetMatch1_thenReturnNull() {
        // when
        Match foundMatch = matchService.getMatchById(1L);
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
        // given
        when(matchRepository.findById(any(Long.class))).thenReturn(Optional.of(match1));
        when(matchRepository.save(any(Match.class))).thenAnswer(i -> i.getArguments()[0]);

        // when
        matchService.addPointToMatch(match1.getId(), player1.getId());
        matchService.addPointToMatch(match1.getId(), player1.getId());

        // then
        assertEquals(Points.THIRTY, player1.getPoints(), "Player 1 moet op 30 staan");
        assertEquals(Points.LOVE, player2.getPoints(), "Player 2 moet op LOVE blijven staan");
    }

    @Test
    public void givenDeuce_whenPlayer1Scores_thenPlayer1HasAdvantage() {
        // given
        player1.setPoints(Points.FORTY);
        player2.setPoints(com.thomas.tennis.Enums.Points.FORTY);
        
        when(matchRepository.findById(any(Long.class))).thenReturn(Optional.of(match1));
        when(matchRepository.save(any(Match.class))).thenAnswer(i -> i.getArguments()[0]);

        // when
        matchService.addPointToMatch(match1.getId(), player1.getId());

        // then
        assertEquals(com.thomas.tennis.Enums.Points.ADV, player1.getPoints(), "Player 1 moet Advantage hebben");
        assertEquals(com.thomas.tennis.Enums.Points.FORTY, player2.getPoints());
    }

    @Test
    public void givenPlayerAtForty_whenPlayerScoresAgainstThirty_thenGameIncrementsAndPointsReset() {
        // given
        player1.setPoints(Points.THIRTY);
        player2.setPoints(Points.FORTY);
        // when
        when(matchRepository.findById(match1.getId())).thenReturn(Optional.of(match1));
        when(matchRepository.save(any(Match.class))).thenAnswer(i -> i.getArguments()[0]);

        matchService.addPointToMatch(match1.getId(), player2.getId());
        // then
        assertEquals( 0, player1.getGames(), "Games moeten 0 blijven");
        assertEquals( 1, player2.getGames(), "Games moeten met 1 stijgen");
        assertEquals(Points.LOVE, player1.getPoints(), "Winnende speler reset naar LOVE");
        assertEquals(Points.LOVE, player2.getPoints(), "Verliezende speler reset naar LOVE");
    }

    @Test
    public void givenAdvantageBattle_whenWinnerScores_thenGameIncrementsAfterDeuce() {
        // given
        player1.setPoints(Points.ADV);
        player2.setPoints(Points.FORTY);
        int initialGames = player1.getGames();

        when(matchRepository.findById(match1.getId())).thenReturn(Optional.of(match1));
        when(matchRepository.save(any(Match.class))).thenAnswer(i -> i.getArguments()[0]);

        // when
        matchService.addPointToMatch(match1.getId(), player1.getId());

        // then
        assertEquals(initialGames + 1, player1.getGames());
        assertEquals(Points.LOVE, player1.getPoints());
        assertEquals(Points.LOVE, player2.getPoints());
    }
}