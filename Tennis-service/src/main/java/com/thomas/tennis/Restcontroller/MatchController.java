package com.thomas.tennis.Restcontroller;

import com.thomas.tennis.Model.Match;
import com.thomas.tennis.Service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping
    public List<Match> getAllMatches() {
        return matchService.getMatches();
    }

    @GetMapping("/{id}")
    public Match getMatchById(@PathVariable Long id) {
        return matchService.getMatchById(id);
    }

    @PostMapping
    public Match createMatch(@RequestParam String namePlayer1, @RequestParam String namePlayer2) {
        return matchService.createMatch(namePlayer1, namePlayer2);
    }

    @PostMapping("/{matchId}/score")
    public Match addPoint(@PathVariable Long matchId, @RequestParam Long playerId) throws Exception {
        return matchService.addPointToMatch(matchId, playerId);
    }

    @PostMapping("/{matchId}/cancel")
    public Match cancelMatch(@PathVariable Long matchId) throws Exception {
        return matchService.cancelMatch(matchId);
    }
}