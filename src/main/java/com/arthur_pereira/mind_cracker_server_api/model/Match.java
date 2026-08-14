package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.deck.DeckType;
import com.arthur_pereira.mind_cracker_server_api.data.match.MatchPlayers;
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
            name = "running_game_used_cards",
            joinColumns = @JoinColumn(name = "running_game_id")
    )
    @Column
    private List<Long> gameUsedCards = new ArrayList<>();

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

    public Match(Deck matchDeck, int matchDeckVersion, String matchPassword, RunningPlayer matchConductor, DeckType matchType) {
        this.matchDeck = matchDeck;
        this.matchDeckVersion = matchDeckVersion;
        this.matchPassword = matchPassword;
        this.matchConductor = matchConductor;
        if(matchType == DeckType.OPTIONAL) {
            throw new DomainException("Match must have a defined type!");
        }
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

    public List<Long> getGameUsedCards() {
        return gameUsedCards;
    }

    public void addUsedCard(Long usedCardId) {
        getGameUsedCards().add(usedCardId);
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
}
