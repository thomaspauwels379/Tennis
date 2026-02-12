package com.thomas.tennis.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thomas.tennis.Enums.MatchState;
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

    public List<Match> getMatches(){
        List<Match> matches = matchRepository.findAll();
        return matches;
    }

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

    public void cancelMatch(long matchId){
        matchRepository.deleteById(matchId);
    }


    @Transactional
    public Match addPointToMatch(long matchId, long playerId)throws Exception{
        Match match = getMatchById(matchId);
        if(match == null){
            throw new Exception("De match met deze id is niet gevonden.");
        }
        if(match.getState() != MatchState.ONGOING){
            throw new Exception("De match is afgelopen of afgesloten.");
        }
        match.scorePoint(playerId);
        Match savedMatch = matchRepository.save(match);
        return savedMatch;
    }
}
