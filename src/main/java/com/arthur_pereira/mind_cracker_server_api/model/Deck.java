package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.card.CardCategoriesList;
import com.arthur_pereira.mind_cracker_server_api.data.common.GameName;
import com.arthur_pereira.mind_cracker_server_api.data.deck.DeckCommonCards;
import com.arthur_pereira.mind_cracker_server_api.data.deck.DeckType;
import com.arthur_pereira.mind_cracker_server_api.data.deck.LoadingType;
import com.arthur_pereira.mind_cracker_server_api.exception.BadLoadAttemptException;
import jakarta.persistence.*;
import org.hibernate.annotations.Audited;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Audited
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

    @Embedded
    private DeckCommonCards deckCommonCards;

    @Column
    @Enumerated(value = EnumType.ORDINAL)
    private DeckType deckType;

    @OneToOne(
            mappedBy = "associatedDeck",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Board deckBoard = null;

    @OneToMany(orphanRemoval = true)
    private List<SpecialCard> deckSpecialCards = new ArrayList<>();

    public Deck() {
    }

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
            if(deckCommonCards.hasEnoughCommonCards(1)) {
                return LoadingType.POSSIBLE_LEADERBOARD;
            } else {
                return LoadingType.IMPOSSIBLE_MISSING_CARDS;
            }
        } else {
            if(deckBoard == null) {
                return LoadingType.IMPOSSIBLE_MISSING_BOARD;
            } else if(deckCommonCards.hasEnoughCommonCards(deckBoard.getMaxBoardLength())) {
                return LoadingType.IMPOSSIBLE_MISSING_CARDS;
            } else {
                return LoadingType.POSSIBLE_BOARD;
            }
        }
    }

    public void associateBoard(Board board) {
        deckBoard = board;
        board.setAssociatedDeck(this);
    }

    public Board getBoard() {
        return deckBoard;
    }

    public GameName getDeckName() {
        return deckName;
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

    public List<SpecialCard> getDeckSpecialCards() {
        return deckSpecialCards;
    }

    public void setDeckSpecialCards(ArrayList<SpecialCard> deckSpecialCards) {
        this.deckSpecialCards = deckSpecialCards;
    }

    public Long getDeckId() {
        return deckId;
    }

    public DeckType getDeckType() {
        return deckType;
    }

    public DeckCommonCards getDeckCommonCards() {
        return deckCommonCards;
    }

    public void setDeckCommonCards(DeckCommonCards deckCommonCards) {
        this.deckCommonCards = deckCommonCards;
    }
}
