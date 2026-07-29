package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.*;
import com.arthur_pereira.mind_cracker_server_api.exception.BadLoadAttemptException;
import jakarta.persistence.*;

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

    @Embedded
    private CardCategoriesList deckCategories;

    @OneToMany(orphanRemoval = true)
    private DeckCards deckCards = new DeckCards();

    @Column
    @Enumerated(value = EnumType.ORDINAL)
    private DeckType deckType;

    @Column
    private Board deckBoard = null;

    public Deck(User deckAuthor, DeckType deckType, GameName deckName) {
        this.deckAuthor = deckAuthor;
        this.deckType = deckType;
        this.deckName = deckName;
    }

    public LoadingType simulateLoading(DeckType loadAttempt) {
        if(loadAttempt == null || loadAttempt == DeckType.OPTIONAL) {
            throw new BadLoadAttemptException("Deck type is undefined.");
        } else if(loadAttempt != deckType) {
            throw new BadLoadAttemptException("Expected Deck Type doesn't match the actual Deck Type.");
        } else if(loadAttempt == DeckType.LEADERBOARD) {
            if(!deckCards.hasEnoughDefaultCards(1)) {
                return LoadingType.POSSIBLE_LEADERBOARD;
            } else {
                return LoadingType.IMPOSSIBLE_MISSING_CARDS;
            }
        } else {
            if(deckBoard == null) {
                return LoadingType.IMPOSSIBLE_MISSING_BOARD;
            } else if(deckCards.hasEnoughDefaultCards(deckBoard.getBoardLength())) {
                return LoadingType.IMPOSSIBLE_MISSING_CARDS;
            } else {
                return LoadingType.POSSIBLE_BOARD;
            }
        }
    }

    public void associateBoard(Board board) {
        deckBoard = board;
    }

    public Board getBoard() {
        return deckBoard;
    }

    public GameName getDeckName() {
        return deckName;
    }

    public String getDeckNameValue() {
        return deckName.getValue();
    }

    public void setDeckName(GameName deckName) {
        this.deckName = deckName;
    }

    public User getAuthor() {
        return deckAuthor;
    }

    public CardCategoriesList getDeckCategories() {
        return deckCategories;
    }

    public void setDeckCategories(CardCategoriesList deckCategories) {
        this.deckCategories = deckCategories;
    }

    public DeckCards getDeckCards() {
        return deckCards;
    }

    public Long getDeckId() {
        return deckId;
    }
}
