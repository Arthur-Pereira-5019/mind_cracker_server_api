package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.card.CardDifficulty;
import com.arthur_pereira.mind_cracker_server_api.data.card.Tips;
import com.arthur_pereira.mind_cracker_server_api.data.common.GameName;
import com.arthur_pereira.mind_cracker_server_api.exception.common.DomainException;
import jakarta.persistence.*;
import org.hibernate.annotations.Audited;

import java.util.Objects;

@Entity
@Audited
public class CommonCard extends AbstractCard{
    @Embedded
    private GameName cardTitle;

    @Embedded
    private Tips cardTips;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private CardDifficulty cardDifficulty;

    @OneToOne
    private CardCategory cardCategory;

    public CommonCard() {
    }

    public CommonCard(CardCategory cardCategory, CardDifficulty cardDifficulty, GameName cardTitle, Tips cardTips) {
        super();
        this.cardCategory = cardCategory;
        this.cardDifficulty = cardDifficulty;
        this.cardTitle = cardTitle;
        this.cardTips = cardTips;
    }

    public Tips getCardTips() {
        return cardTips;
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
