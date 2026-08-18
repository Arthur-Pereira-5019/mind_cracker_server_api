package com.arthur_pereira.mind_cracker_server_api.service;

import com.arthur_pereira.mind_cracker_server_api.data.board.BoardPositionType;
import com.arthur_pereira.mind_cracker_server_api.data.card.CardDifficulty;
import com.arthur_pereira.mind_cracker_server_api.data.deck.DeckCommonCards;
import com.arthur_pereira.mind_cracker_server_api.data.deck.DeckType;
import com.arthur_pereira.mind_cracker_server_api.dto.match.CreateMatchDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.match.JoinMatchDTO;
import com.arthur_pereira.mind_cracker_server_api.exception.common.ResourceNotFoundException;
import com.arthur_pereira.mind_cracker_server_api.exception.match.UnableToJoinMatchException;
import com.arthur_pereira.mind_cracker_server_api.model.*;
import com.arthur_pereira.mind_cracker_server_api.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

public class MatchService {
    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private DeckService deckService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommonCardService commonCardService;

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

    public CommonCard getCurrentCard(Long matchId) {
        Match match = findMatchById(matchId);
        return commonCardService.findCardById(match.getCurrentCardId());
    }

    public Match nextRound(Long matchId) {
        Match match = findMatchById(matchId);
        match.resetUsedCardTips();
        match.incrementRound();
        if(match.getMatchType() == DeckType.BOARD_GAME) {
            return nextRoundBoard(match);
        }
        return nextRoundLeaderboard(match);
    }

    public Match nextRoundLeaderboard(Match match) {
        long chance = System.nanoTime() % 10;
        List<Long> usedCards = match.getGameUsedCommonCards();
        CommonCard card;
        DeckCommonCards deckCommonCards = match.getMatchDeck().getDeckCommonCards();
        if(chance < 6) {
            card = deckCommonCards.shuffleCommonCardOfType(CardDifficulty.EASY,usedCards);
        } else if (chance < 9) {
            card = deckCommonCards.shuffleCommonCardOfType(CardDifficulty.MEDIUM, usedCards);
        } else {
            card = deckCommonCards.shuffleCommonCardOfType(CardDifficulty.HARD, usedCards);
        }
        match.setCurrentCardId(card.getCardId());
        return match;
    }


    public Match nextRoundBoard(Match match) {
        int positionOfTheFrontPlayer = match.getMatchPlayers().getCurrentPlayer().getScore();

        BoardPositionType boardPositionType =
                match.getMatchDeck().getBoard().getPositionTypeAt(positionOfTheFrontPlayer);

        if(boardPositionType.isACardDifficulty()) {
            CommonCard card = match.getMatchDeck().getDeckCommonCards().shuffleCommonCardOfType(boardPositionType.toCardDifficulty(), match.getGameUsedCommonCards());
            match.addUsedCommonCard(card.getCardId());
            match.setCurrentCardId(card.getCardId());
        } else {
            SpecialCard specialCard = match.getMatchDeck().shuffleSpecialCardOfType(match.getGameUsedSpecialCards());
            match.addUsedSpecialCard(specialCard.getCardId());
            //TODO ACTUAL EFFECT OVER MATCH
        }
        return match;
    }


    public Match findMatchById(Long id) {
        return matchRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Couldn't find Match with the provided Id"));
    }
}
