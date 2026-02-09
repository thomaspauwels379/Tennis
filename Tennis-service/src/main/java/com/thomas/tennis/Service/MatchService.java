package com.thomas.tennis.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thomas.tennis.Model.Match;
import com.thomas.tennis.Model.Player;
import com.thomas.tennis.Repo.MatchRepository;

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

}
