package com.thomas.tennis.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thomas.tennis.Enums.Points;
import com.thomas.tennis.Model.Match;
import com.thomas.tennis.Model.Player;
import com.thomas.tennis.Repo.MatchRepository;

import jakarta.transaction.Transactional;

@Service
public class MatchService {
        
    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private PlayerService playerService;

    public Match getMatchById(long matchId){
        Optional<Match> Omatch = matchRepository.findById(matchId);
        if(Omatch.isPresent()){
            return Omatch.get();
        }
        else{
            return null;
        }
    }

    public Match createMatch(String namePlayer1, String namePlayer2){
        List<Player> players = playerService.createPlayers(namePlayer1,namePlayer2);
        Match match = new Match(players.get(0),players.get(1));
        return matchRepository.save(match);
    }

    @Transactional
    public Match addPointToMatch(long matchId, long playerId){
        Match match = getMatchById(matchId);

        Player player;
        Player opponent;
        if (match.getPlayer1().getId() == playerId) {
            player = match.getPlayer1();
            opponent = match.getPlayer2();
        } else {
            player = match.getPlayer2();
            opponent = match.getPlayer1();
        }
        player.addPoint(opponent);

        return matchRepository.save(match);
    }
}
