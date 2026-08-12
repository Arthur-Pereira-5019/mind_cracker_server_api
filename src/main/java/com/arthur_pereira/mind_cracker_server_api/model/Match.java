package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.deck.DeckType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long matchId;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<RunningPlayer> matchPlayers = new ArrayList<>();

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
    private List<Integer> gameCurrentCardTips = new ArrayList<>();

    @Column
    private int matchPlayerPointer = 0;

    @Column
    private int matchDeckVersion;

    @Column
    private int currentRound;

    @Column
    private boolean started = false;

    public Match(Deck matchDeck, int matchDeckVersion, String matchPassword, RunningPlayer matchConductor) {
        this.matchDeck = matchDeck;
        this.matchDeckVersion = matchDeckVersion;
        this.matchPassword = matchPassword;
        matchPlayers.add(matchConductor);
    }

    public void start() {
        started = true;
    }

    public boolean isStarted() {
        return started;
    }

    public void addRunningPlayer(RunningPlayer runningPlayer) {
        this.matchPlayers.add(runningPlayer);
    }

    public void removeRunningPlayer(RunningPlayer runningPlayer) {
        this.matchPlayers.remove(runningPlayer);
    }
}
