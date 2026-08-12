package com.arthur_pereira.mind_cracker_server_api.service;

import com.arthur_pereira.mind_cracker_server_api.dto.match.CreateMatchDTO;
import com.arthur_pereira.mind_cracker_server_api.exception.ResourceNotFoundException;
import com.arthur_pereira.mind_cracker_server_api.exception.UnableToJoinMatchException;
import com.arthur_pereira.mind_cracker_server_api.model.Deck;
import com.arthur_pereira.mind_cracker_server_api.model.Match;
import com.arthur_pereira.mind_cracker_server_api.model.RunningPlayer;
import com.arthur_pereira.mind_cracker_server_api.model.User;
import com.arthur_pereira.mind_cracker_server_api.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MatchService {
    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private DeckService deckService;

    @Autowired
    private UserService userService;

    @Autowired
    private RunningPlayerService runningPlayerService;

    public Match createMatch(CreateMatchDTO createMatchDTO, User user) {
        Deck deck = deckService.findDeckById(createMatchDTO.matchDeckId());
        RunningPlayer conductor = runningPlayerService.createRunningPlayer(-1,user);
        Match match = new Match(deck, 0, createMatchDTO.matchPassword(), conductor);
        return matchRepository.save(match);
    }

    public Match joinMatch(Long matchId, User user) {
        Match match = findMatchById(matchId);
        if(match.isStarted()) {
            throw new UnableToJoinMatchException("Match has already started.");
        }
        user = userService.attemptToJoin(user);
        RunningPlayer runningPlayer = runningPlayerService.createRunningPlayer(0,user);
        match.addRunningPlayer(runningPlayer);
        return matchRepository.save(match);
    }

    public Match nextRound() {

    }

    public Match nextRoundBoard() {

    }

    public Match findMatchById(Long id) {
        return matchRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Couldn't find Match with the provided Id"));
    }
}
