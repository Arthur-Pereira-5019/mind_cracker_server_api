package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.deck.DeckType;
import com.arthur_pereira.mind_cracker_server_api.data.game.GamePlayers;
import com.arthur_pereira.mind_cracker_server_api.data.game.ToleratedAnswerConfiguration;
import com.arthur_pereira.mind_cracker_server_api.exception.common.DomainException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long gameId;

    @Embedded
    private GamePlayers gamePlayers = new GamePlayers();

    @Column
    private String gamePassword = "";

    @ManyToOne
    private Deck gameDeck;

    @Column
    @Enumerated
    private DeckType gameType;

    @ElementCollection
    @CollectionTable(
            name = "running_game_used_common_cards",
            joinColumns = @JoinColumn(name = "gameId")
    )
    @Column
    private List<Long> gameUsedCommonCards = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "running_game_used_special_cards",
            joinColumns = @JoinColumn(name = "gameId")
    )
    @Column
    private List<Long> gameUsedSpecialCards = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "running_game_used_tips",
            joinColumns = @JoinColumn(name = "gameId")
    )
    @Column
    private List<Integer> currentUsedTips = new ArrayList<>();

    @Column
    private int gameDeckVersion;

    @Column
    private int currentRound;

    @OneToOne
    private RunningPlayer gameConductor;

    @Column
    private boolean started = false;

    @Column
    private Long currentCardId;

    @Column
    private int antiMemorizationCipher;

    @Column
    private ToleratedAnswerConfiguration toleratedAnswerConfiguration;

    public Game() {
    }

    public Game(Deck gameDeck, int gameDeckVersion, String gamePassword, RunningPlayer gameConductor, DeckType gameType, ToleratedAnswerConfiguration toleratedAnswerConfiguration) {
        this.gameDeck = gameDeck;
        this.gameDeckVersion = gameDeckVersion;
        this.gamePassword = gamePassword;
        this.gameConductor = gameConductor;
        this.toleratedAnswerConfiguration = toleratedAnswerConfiguration;
        if(gameType == DeckType.OPTIONAL) {
            throw new DomainException("Game must have a defined type!");
        }
    }

    public void incrementRound() {
        antiMemorizationCipher = Long.valueOf(System.nanoTime()).intValue() % 25;
        currentRound += 1;
    }

    public Long getGameId() {
        return gameId;
    }

    public GamePlayers getGamePlayers() {
        return gamePlayers;
    }

    public void start() {
        started = true;
    }

    public boolean isStarted() {
        return started;
    }

    public Deck getGameDeck() {
        return gameDeck;
    }

    public String getGamePassword() {
        return gamePassword;
    }

    public DeckType getGameType() {
        return gameType;
    }

    public List<Long> getGameUsedCommonCards() {
        return gameUsedCommonCards;
    }

    public List<Long> getGameUsedSpecialCards() {
        return gameUsedCommonCards;
    }

    public void addUsedCommonCard(Long usedCardId) {
        gameUsedCommonCards.add(usedCardId);
    }

    public void addUsedSpecialCard(Long usedCardId) {
        gameUsedSpecialCards.add(usedCardId);
    }

    public void resetUsedCardTips() {
        this.currentUsedTips = new ArrayList<>();
    }

    public Long getCurrentCardId() {
        return currentCardId;
    }

    public void setCurrentCardId(Long currentCardId) {
        this.currentCardId = currentCardId;
    }

    public void setGamePlayers(GamePlayers gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public RunningPlayer getGameConductor() {
        return gameConductor;
    }

    public List<Integer> getCurrentUsedTips() {
        return currentUsedTips;
    }

    public void addUsedTip(int tip) {
        currentUsedTips.add(tip);
    }

    public int getAntiMemorizationCipher() {
        return antiMemorizationCipher;
    }

    public ToleratedAnswerConfiguration getToleratedAnswerConfiguration() {
        return toleratedAnswerConfiguration;
    }

    public void setToleratedAnswerConfiguration(ToleratedAnswerConfiguration toleratedAnswerConfiguration) {
        this.toleratedAnswerConfiguration = toleratedAnswerConfiguration;
    }


    public void kickAllPlayers() {
        this.gamePlayers.empty();
        this.gameConductor = null;
    }
}
