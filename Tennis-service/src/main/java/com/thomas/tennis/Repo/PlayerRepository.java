package com.thomas.tennis.Repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.thomas.tennis.Model.Player;



public interface PlayerRepository extends JpaRepository<Player, Long> {
    public Player findPlayerById(String title);
}
