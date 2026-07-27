package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.DeckType;
import com.arthur_pereira.mind_cracker_server_api.data.GameName;
import com.arthur_pereira.mind_cracker_server_api.data.LoadingType;
import com.arthur_pereira.mind_cracker_server_api.exception.BadLoadAttempt;
import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table
public class Deck {
    @Id
    @GeneratedValue
    private Long deckId;

    @Embedded
    private GameName deckName;

    @ManyToOne
    private User deckAuthor;

    @OneToMany
    private ArrayList<CardDeckCategory> deckCategories = new ArrayList<>();

    @OneToMany
    private ArrayList<Card> deckCards = new ArrayList<>();

    @Column
    @Enumerated(value = EnumType.ORDINAL)
    private DeckType deckType;

    @Column
    private Board deckBoard = null;

    public LoadingType canBeLoaded(DeckType loadAttempt) {
        if(loadAttempt == null || loadAttempt == DeckType.OPTIONAL) {
            throw new BadLoadAttempt("Deck type is undefined.");
        } else if(loadAttempt != deckType) {
            throw new BadLoadAttempt("Expected Deck Type doesn't match the actual Deck Type.");
        } else if(loadAttempt == DeckType.LEADERBOARD) {
            if(!deckCards.isEmpty()) {
                return LoadingType.POSSIBLE_LEADERBOARD;
            } else {
                return LoadingType.IMPOSSIBLE_MISSING_CARDS;
            }
        } else {
            if(deckBoard == null) {
                return LoadingType.IMPOSSIBLE_MISSING_BOARD;
            } else if(deckCards.size() > deckBoard.getBoardLength()) {
                return LoadingType.IMPOSSIBLE_MISSING_CARDS;
            } else {
                return LoadingType.POSSIBLE_BOARD;
            }
        }
    }

}
