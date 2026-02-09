package com.thomas.tennis.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thomas.tennis.Model.Match;
import com.thomas.tennis.Repo.MatchRepository;

@Service
public class MatchService {
        
    @Autowired
    private MatchRepository matchRepository;


    public Match getMatchById(long matchId){
        Optional<Match> Omatch = matchRepository.findById(matchId);
        if(Omatch.isPresent()){
            return Omatch.get();
        }
        else{
            return null;
        }
    }
}
