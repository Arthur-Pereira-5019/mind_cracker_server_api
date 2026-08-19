package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.deck.DeckType;
import com.arthur_pereira.mind_cracker_server_api.data.match.MatchPlayers;
import com.arthur_pereira.mind_cracker_server_api.data.match.ToleratedAnswerConfiguration;
import com.arthur_pereira.mind_cracker_server_api.exception.common.DomainException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long matchId;

    @Embedded
    private MatchPlayers matchPlayers = new MatchPlayers();

    @Column
    private String matchPassword = "";

    @ManyToOne
    private Deck matchDeck;

    @Column
    @Enumerated
    private DeckType matchType;

    @ElementCollection
    @CollectionTable(
            name = "running_game_used_common_cards",
            joinColumns = @JoinColumn(name = "running_game_id")
    )
    @Column
    private List<Long> gameUsedCommonCards = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "running_game_used_special_cards",
            joinColumns = @JoinColumn(name = "running_game_id")
    )
    @Column
    private List<Long> gameUsedSpecialCards = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "running_game_used_tips",
            joinColumns = @JoinColumn(name = "running_game_id")
    )
    @Column
    private List<Integer> currentUsedTips = new ArrayList<>();

    @Column
    private int matchDeckVersion;

    @Column
    private int currentRound;

    @OneToOne
    private RunningPlayer matchConductor;

    @Column
    private boolean started = false;

    @Column
    private Long currentCardId;

    @Column
    private int antiMemorizatonCipher;

    @Column
    private ToleratedAnswerConfiguration toleratedAnswerConfiguration;

    public Match(Deck matchDeck, int matchDeckVersion, String matchPassword, RunningPlayer matchConductor, DeckType matchType, ToleratedAnswerConfiguration toleratedAnswerConfiguration) {
        this.matchDeck = matchDeck;
        this.matchDeckVersion = matchDeckVersion;
        this.matchPassword = matchPassword;
        this.matchConductor = matchConductor;
        this.toleratedAnswerConfiguration = toleratedAnswerConfiguration;
        if(matchType == DeckType.OPTIONAL) {
            throw new DomainException("Match must have a defined type!");
        }
    }

    public void incrementRound() {
        antiMemorizatonCipher = Long.valueOf(System.nanoTime()).intValue() % 25;
        currentRound += 1;
    }

    public MatchPlayers getMatchPlayers() {
        return matchPlayers;
    }

    public void start() {
        started = true;
    }

    public boolean isStarted() {
        return started;
    }

    public Deck getMatchDeck() {
        return matchDeck;
    }

    public String getMatchPassword() {
        return matchPassword;
    }

    public DeckType getMatchType() {
        return matchType;
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

    public void setMatchPlayers(MatchPlayers matchPlayers) {
        this.matchPlayers = matchPlayers;
    }

    public RunningPlayer getMatchConductor() {
        return matchConductor;
    }

    public List<Integer> getCurrentUsedTips() {
        return currentUsedTips;
    }

    public void addUsedTip(int tip) {
        currentUsedTips.add(tip);
    }

    public int getAntiMemorizatonCipher() {
        return antiMemorizatonCipher;
    }

    public ToleratedAnswerConfiguration getToleratedAnswerConfiguration() {
        return toleratedAnswerConfiguration;
    }

    public void setToleratedAnswerConfiguration(ToleratedAnswerConfiguration toleratedAnswerConfiguration) {
        this.toleratedAnswerConfiguration = toleratedAnswerConfiguration;
    }
}
