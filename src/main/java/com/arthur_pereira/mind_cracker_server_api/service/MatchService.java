package com.arthur_pereira.mind_cracker_server_api.service;

import com.arthur_pereira.mind_cracker_server_api.data.deck.DeckType;
import com.arthur_pereira.mind_cracker_server_api.dto.match.CreateMatchDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.match.JoinMatchDTO;
import com.arthur_pereira.mind_cracker_server_api.exception.ResourceNotFoundException;
import com.arthur_pereira.mind_cracker_server_api.exception.UnableToJoinMatchException;
import com.arthur_pereira.mind_cracker_server_api.model.Deck;
import com.arthur_pereira.mind_cracker_server_api.model.Match;
import com.arthur_pereira.mind_cracker_server_api.model.RunningPlayer;
import com.arthur_pereira.mind_cracker_server_api.model.User;
import com.arthur_pereira.mind_cracker_server_api.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

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
        deck.simulateLoading(createMatchDTO.gameType());
        RunningPlayer conductor = runningPlayerService.createRunningPlayer(-1,user);
        Match match = new Match(deck, 0, createMatchDTO.matchPassword(), conductor, createMatchDTO.gameType());
        return matchRepository.save(match);
    }

    public Match joinMatch(JoinMatchDTO joinMatchDTO, User user) {
        Match match = findMatchById(joinMatchDTO.matchId());
        if(match.isStarted()) {
            throw new UnableToJoinMatchException("Match has already started.");
        }
        if(!Objects.equals(joinMatchDTO.password(), match.getMatchPassword())) {
            throw new UnableToJoinMatchException("The given password doesn't match the Match actual password.");
        }
        user = userService.attemptToJoin(user);
        RunningPlayer runningPlayer = runningPlayerService.createRunningPlayer(0,user);
        match.getMatchPlayers().addRunningPlayer(runningPlayer);
        return matchRepository.save(match);
    }

    public Match nextRound(Long matchId) {
        Match match = findMatchById(matchId);
        if(match.getMatchType() == DeckType.BOARD_GAME) {
            return nextRoundBoard(match);
        }
        return nextRoundLeaderboard(match);
    }

    public Match nextRoundBoard(Match match) {
        match.incrementMatchPlayerPointer();
        return match;
    }

    public Match nextRoundLeaderboard(Match match) {
        return match;
    }

    public Match findMatchById(Long id) {
        return matchRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Couldn't find Match with the provided Id"));
    }
}
