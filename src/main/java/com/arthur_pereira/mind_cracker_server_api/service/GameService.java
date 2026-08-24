package com.arthur_pereira.mind_cracker_server_api.service;

import com.arthur_pereira.mind_cracker_server_api.data.board.BoardPositionType;
import com.arthur_pereira.mind_cracker_server_api.data.card.CardDifficulty;
import com.arthur_pereira.mind_cracker_server_api.data.deck.DeckCommonCards;
import com.arthur_pereira.mind_cracker_server_api.data.deck.DeckType;
import com.arthur_pereira.mind_cracker_server_api.dto.game.CreateGameDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.game.JoinGameDTO;
import com.arthur_pereira.mind_cracker_server_api.exception.common.ResourceNotFoundException;
import com.arthur_pereira.mind_cracker_server_api.exception.game.IllegalMoveException;
import com.arthur_pereira.mind_cracker_server_api.exception.game.UnableToJoinGameException;
import com.arthur_pereira.mind_cracker_server_api.exception.security.UnauthorizedActionException;
import com.arthur_pereira.mind_cracker_server_api.model.*;
import com.arthur_pereira.mind_cracker_server_api.repository.GamePlayerRepository;
import com.arthur_pereira.mind_cracker_server_api.repository.GameRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Objects;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

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

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Transactional
    public Game createGame(CreateGameDTO createGameDTO, User user) {
        try {
            Deck deck = deckService.findDeckById(createGameDTO.gameDeckId());
            deck.simulateLoading(createGameDTO.gameType());
            userService.attemptToJoin(user);
            RunningPlayer conductor = runningPlayerService.createRunningPlayer(user);
            Game game = new Game(deck, 0, createGameDTO.gamePassword(), conductor,
                    createGameDTO.gameType(), createGameDTO.toleratedAnswerConfiguration());
            return gameRepository.save(game);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    public Game joinGame(JoinGameDTO joinGameDTO, User user) {
        Game game = findGameById(joinGameDTO.gameId());
        if(game.isStarted()) {
            throw new UnableToJoinGameException("Game has already started.");
        }
        if(!Objects.equals(joinGameDTO.password(), game.getGamePassword())) {
            throw new UnableToJoinGameException("The given password doesn't game the Game actual password.");
        }
        user = userService.attemptToJoin(user);
        RunningPlayer runningPlayer = runningPlayerService.createRunningPlayer(user);
        game.getGamePlayers().addRunningPlayer(runningPlayer);
        return gameRepository.save(game);
    }

    public Game leaveGame(Long gameId, User user) {
        Game game = findGameAssuringIsPlayer(gameId, user);
        game.getGamePlayers().removeRunningPlayer(
                runningPlayerService.findPlayerByUserId(user.getId()));
        return gameRepository.save(game);
    }

    public Game goToNextPlayer(Long gameId, User conductor) {
        Game game = findGameAssuringIsConductor(gameId, conductor);
        GamePlayers gamePlayers = game.getGamePlayers();
        gamePlayers.goToNextPlayer();
        game.setGamePlayers(gamePlayers);
        return gameRepository.save(game);
    }

    public CommonCard getCurrentCard(Long gameId, User user) {
        Game game = findGameAssuringIsConductor(gameId, user);
        return commonCardService.findCardById(game.getCurrentCardId());
    }

    private CommonCard getCurrentCard(Game game) {
        return commonCardService.findCardById(game.getCurrentCardId());
    }

    public void askATip(Long gameId, Integer tipPosition, User user) {
        Game game = findGameAssuringIsCurrentPlayer(gameId, user);
        if(game.getCurrentUsedTips().contains(tipPosition)) {
            throw new IllegalMoveException("The provided tip already was used!");
        }
        game.addUsedTip(tipPosition);
    }

    public boolean attemptAnswer(Long gameId, User user, String givenAnswer) {
        Game game = findGameAssuringIsCurrentPlayer(gameId, user);
        CommonCard commonCard = getCurrentCard(game);
        String expectedAnswer = commonCard.getCardTitle().getValue();
        if(stringManipulationService.matchUnformattedStrings(expectedAnswer, givenAnswer,
                game.getToleratedAnswerConfiguration().value)) {
            runningPlayerService.makePlayerScore(commonCard.getCardTips().getNumberOfTips() -
                    game.getCurrentUsedTips().size(), game.getGamePlayers().getCurrentPlayer().getId()
            );
            return true;
        }
        return false;
    }

    @Transactional
    public void shutdownEveryGame() {
        userService.markEveryUserAsNotPlaying();
        gameRepository.deleteAll();
        gameRepository.flush();
    }

    public void shutdownAGame(Game game) {
        userService.markEveryUserOfAGameAsNotPlaying(game.getGameId());
        gameRepository.delete(game);
    }

    private void playerScore(RunningPlayer player) {

    }

    public List<String> getAllTips(Long gameId, User user) {
        Game game = findGameAssuringIsConductor(gameId, user);
        return getCurrentCard(gameId, user).getCardTips().getUsedTips(game.getCurrentUsedTips(),
                game.getAntiMemorizationCipher());
    }

    public Game nextRound(Long gameId, User conductor) {
        Game game = findGameAssuringIsConductor(gameId, conductor);
        game.resetUsedCardTips();
        game.incrementRound();
        if(game.getGameType() == DeckType.BOARD_GAME) {
            return gameRepository.save(nextRoundBoard(game));
        }
        return gameRepository.save(nextRoundLeaderboard(game));
    }

    public Game nextRoundLeaderboard(Game game) {
        long chance = System.nanoTime() % 10;
        List<Long> usedCards = game.getGameUsedCommonCards();
        CommonCard card;
        DeckCommonCards deckCommonCards = game.getGameDeck().getDeckCommonCards();
        if(chance < 6) {
            card = deckCommonCards.shuffleCommonCardOfType(CardDifficulty.EASY,usedCards);
        } else if (chance < 9) {
            card = deckCommonCards.shuffleCommonCardOfType(CardDifficulty.MEDIUM, usedCards);
        } else {
            card = deckCommonCards.shuffleCommonCardOfType(CardDifficulty.HARD, usedCards);
        }
        game.setCurrentCardId(card.getCardId());
        return game;
    }


    public Game nextRoundBoard(Game game) {
        int positionOfTheFrontPlayer = game.getGamePlayers().getCurrentPlayer().getScore();

        BoardPositionType boardPositionType =
                game.getGameDeck().getBoard().getPositionTypeAt(positionOfTheFrontPlayer);

        if(boardPositionType.isACardDifficulty()) {
            CommonCard card = game.getGameDeck().getDeckCommonCards().shuffleCommonCardOfType(boardPositionType.toCardDifficulty(), game.getGameUsedCommonCards());
            game.addUsedCommonCard(card.getCardId());
            game.setCurrentCardId(card.getCardId());
        } else {
            SpecialCard specialCard = game.getGameDeck().shuffleSpecialCardOfType(game.getGameUsedSpecialCards());
            game.addUsedSpecialCard(specialCard.getCardId());
            //TODO ACTUAL EFFECT OVER GAME
        }
        return game;
    }


    public Game findGameById(Long id) {
        return gameRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Couldn't find Game with the provided Id"));
    }

    public Game findGameAssuringIsPlayer(Long gameId, User user) {
        Game game = findGameById(gameId);
        if(game.getGamePlayers().isUserAPlayer(user)) {
            return game;
        }
        throw new UnauthorizedActionException("You aren't a Player of the provided Game.");
    }

    public Game findGameAssuringIsCurrentPlayer(Long gameId, User user) {
        Game game = findGameById(gameId);
        if(game.getGamePlayers().isUserCurrentPlayer(user)) {
            return game;
        }
        throw new UnauthorizedActionException("You aren't the Current Player of the provided Game.");
    }

    public Game findGameAssuringIsConductor(Long gameId, User user) {
        Game game = findGameById(gameId);
        if(game.getGameConductor().getRelatedUserId().equals(user.getId())) {
            return game;
        }
        throw new UnauthorizedActionException("You aren't the Conductor of the provided Game.");
    }
}
