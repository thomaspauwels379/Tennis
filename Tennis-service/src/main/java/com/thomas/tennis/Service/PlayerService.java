package com.thomas.tennis.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.thomas.tennis.Model.Player;
import com.thomas.tennis.Repo.PlayerRepository;

@Service
public class PlayerService {
        
    @Autowired
    private PlayerRepository playerRepository;


    public Player getPlayerById(long matchId){
        Optional<Player> Oplayer = playerRepository.findById(matchId);
        if(Oplayer.isPresent()){
            return Oplayer.get();
        }
        else{
            return null;
        }
    }

    public List<Player> createPlayers(String namePlayer1, String namePlayer2) {
        Player p1 = new Player(namePlayer1);
        Player p2 = new Player(namePlayer2);
        return playerRepository.saveAll(List.of(p1, p2));
    }
}
