package com.thomas.tennis.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thomas.tennis.Model.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {
    public Match findMatchById(String title);
}
