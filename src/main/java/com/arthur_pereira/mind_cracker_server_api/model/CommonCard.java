package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.card.CardDifficulty;
import com.arthur_pereira.mind_cracker_server_api.data.common.GameName;
import com.arthur_pereira.mind_cracker_server_api.exception.DomainException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class CommonCard extends AbstractCard{
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

    @OneToOne
    private CardCategory cardCategory;

    public CommonCard() {
    }

    public CommonCard(CardCategory cardCategory, CardDifficulty cardDifficulty, GameName cardTitle, List<String> tips) {
        super();
        this.cardCategory = cardCategory;
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

    public CardCategory getCardDeckCategory() {
        return cardCategory;
    }

    public void setCardDeckCategory(CardCategory cardCategory) {
        if(Objects.equals(cardCategory.getAssociatedDeck().getDeckId(), getAssociatedDeck().getDeckId())) {
            this.cardCategory = cardCategory;
        } else {
            throw new DomainException("Associated Decks don't match between Deck Category and CommonCard.");
        }
    }

    public void setCardDifficulty(CardDifficulty cardDifficulty) {
        this.cardDifficulty = cardDifficulty;
    }

    public void setCardTitle(GameName cardTitle) {
        this.cardTitle = cardTitle;
    }


}
