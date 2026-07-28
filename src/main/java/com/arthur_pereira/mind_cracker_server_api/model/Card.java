package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.CardDifficulty;
import com.arthur_pereira.mind_cracker_server_api.data.GameName;
import com.arthur_pereira.mind_cracker_server_api.exception.DomainException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
public class Card extends AbstractCard{
    @Embedded
    private GameName cardTitle;

    @ElementCollection
    @CollectionTable(
            name = "card_tips",
            joinColumns = @JoinColumn(name = "card_id")
    )
    @Column(name = "tip")
    private List<String> tips = new ArrayList<>();

    @Column
    @Enumerated(EnumType.ORDINAL)
    private CardDifficulty cardDifficulty;

    @Column
    private CardDeckCategory cardDeckCategory;


    public Card(CardDeckCategory cardDeckCategory, CardDifficulty cardDifficulty, GameName cardTitle, List<String> tips, Deck deck) {
        super(deck);
        this.cardDeckCategory = cardDeckCategory;
        this.cardDifficulty = cardDifficulty;
        this.cardTitle = cardTitle;
        this.tips = tips;
    }

    public List<String> getTips() {
        return tips;
    }

    public GameName getCardTitle() {
        return cardTitle;
    }

    public CardDifficulty getCardDifficulty() {
        return cardDifficulty;
    }

    public CardDeckCategory getCardDeckCategory() {
        return cardDeckCategory;
    }

    public void setCardDeckCategory(CardDeckCategory cardDeckCategory) {
        if(Objects.equals(cardDeckCategory.getAssociatedDeck().getDeckId(), getAssociatedDeck().getDeckId())) {
            this.cardDeckCategory = cardDeckCategory;
        } else {
            throw new DomainException("Associated Decks don't match between Deck Category and Card.");
        }
    }

    public void setCardDifficulty(CardDifficulty cardDifficulty) {
        this.cardDifficulty = cardDifficulty;
    }

    public void setCardTitle(GameName cardTitle) {
        this.cardTitle = cardTitle;
    }
}
