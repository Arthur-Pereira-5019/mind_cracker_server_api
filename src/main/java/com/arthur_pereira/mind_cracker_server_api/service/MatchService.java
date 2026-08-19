package com.arthur_pereira.mind_cracker_server_api.service;

import com.arthur_pereira.mind_cracker_server_api.data.board.BoardPositionType;
import com.arthur_pereira.mind_cracker_server_api.data.card.CardDifficulty;
import com.arthur_pereira.mind_cracker_server_api.data.deck.DeckCommonCards;
import com.arthur_pereira.mind_cracker_server_api.data.deck.DeckType;
import com.arthur_pereira.mind_cracker_server_api.data.match.MatchPlayers;
import com.arthur_pereira.mind_cracker_server_api.dto.match.CreateMatchDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.match.JoinMatchDTO;
import com.arthur_pereira.mind_cracker_server_api.exception.common.ResourceNotFoundException;
import com.arthur_pereira.mind_cracker_server_api.exception.match.IllegalMoveException;
import com.arthur_pereira.mind_cracker_server_api.exception.match.UnableToJoinMatchException;
import com.arthur_pereira.mind_cracker_server_api.exception.security.UnauthorizedActionException;
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
    private StringManipulationService stringManipulationService;

    @Autowired
    private RunningPlayerService runningPlayerService;

    public Match createMatch(CreateMatchDTO createMatchDTO, User user) {
        Deck deck = deckService.findDeckById(createMatchDTO.matchDeckId());
        deck.simulateLoading(createMatchDTO.gameType());
        userService.attemptToJoin(user);
        RunningPlayer conductor = runningPlayerService.createRunningPlayer(user);
        Match match = new Match(deck, 0, createMatchDTO.matchPassword(), conductor,
                createMatchDTO.gameType(), createMatchDTO.toleratedAnswerConfiguration());
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
        RunningPlayer runningPlayer = runningPlayerService.createRunningPlayer(user);
        match.getMatchPlayers().addRunningPlayer(runningPlayer);
        return matchRepository.save(match);
    }

    public Match leaveMatch(Long matchId, User user) {
        Match match = findMatchAssuringIsPlayer(matchId, user);
        match.getMatchPlayers().removeRunningPlayer(
                runningPlayerService.findPlayerByUserId(user.getId()));
        return matchRepository.save(match);
    }

    public Match goToNextPlayer(Long matchId, User conductor) {
        Match match = findMatchAssuringIsConductor(matchId, conductor);
        MatchPlayers matchPlayers = match.getMatchPlayers();
        matchPlayers.goToNextPlayer();
        match.setMatchPlayers(matchPlayers);
        return matchRepository.save(match);
    }

    public CommonCard getCurrentCard(Long matchId, User user) {
        Match match = findMatchAssuringIsPlayer(matchId, user);
        return commonCardService.findCardById(match.getCurrentCardId());
    }

    private CommonCard getCurrentCard(Match match) {
        return commonCardService.findCardById(match.getCurrentCardId());
    }

    public void askATip(Long matchId, Integer tipPosition, User user) {
        Match match = findMatchAssuringIsCurrentPlayer(matchId, user);
        if(match.getCurrentUsedTips().contains(tipPosition)) {
            throw new IllegalMoveException("The provided tip already was used!");
        }
        match.addUsedTip(tipPosition);
    }

    public boolean attemptAnswer(Long matchId, User user, String givenAnswer) {
        Match match = findMatchAssuringIsCurrentPlayer(matchId, user);
        String expectedAnswer = getCurrentCard(match).getCardTitle().getValue();
        if(stringManipulationService.matchUnformattedStrings(expectedAnswer, givenAnswer,
                match.getToleratedAnswerConfiguration().value)) {
            return true;
        }
        return false;
    }

    private void playerScore(RunningPlayer player) {

    }

    public List<String> getAllTips(Long matchId, User user) {
        Match match = findMatchAssuringIsPlayer(matchId, user);
        return getCurrentCard(matchId, user).getCardTips().getUsedTips(match.getCurrentUsedTips(),
                match.getAntiMemorizatonCipher());
    }

    public Match nextRound(Long matchId, User conductor) {
        Match match = findMatchAssuringIsConductor(matchId, conductor);
        match.resetUsedCardTips();
        match.incrementRound();
        if(match.getMatchType() == DeckType.BOARD_GAME) {
            return matchRepository.save(nextRoundBoard(match));
        }
        return matchRepository.save(nextRoundLeaderboard(match));
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

    public Match findMatchAssuringIsPlayer(Long matchId, User user) {
        Match match = findMatchById(matchId);
        if(match.getMatchPlayers().isUserAPlayer(user)) {
            return match;
        }
        throw new UnauthorizedActionException("You aren't a Player of the provided Match.");
    }

    public Match findMatchAssuringIsCurrentPlayer(Long matchId, User user) {
        Match match = findMatchById(matchId);
        if(match.getMatchPlayers().isUserCurrentPlayer(user)) {
            return match;
        }
        throw new UnauthorizedActionException("You aren't the Current Player of the provided Match.");
    }

    public Match findMatchAssuringIsConductor(Long matchId, User user) {
        Match match = findMatchById(matchId);
        if(match.getMatchConductor().getRelatedUserId().equals(user.getId())) {
            return match;
        }
        throw new UnauthorizedActionException("You aren't the Conductor of the provided Match.");
    }
}
