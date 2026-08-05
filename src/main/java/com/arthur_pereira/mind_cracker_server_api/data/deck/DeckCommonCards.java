package com.arthur_pereira.mind_cracker_server_api.data.deck;

import com.arthur_pereira.mind_cracker_server_api.model.CommonCard;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class DeckCommonCards {
    @OneToMany(
            mappedBy = "associatedDeck",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CommonCard> deckCommonCards = new ArrayList<>();

    public List<CommonCard> getDeckCommonCards() {
        return deckCommonCards;
    }

    public boolean hasEnoughCommonCards(int n) {
        return deckCommonCards.size() >= n;
    }

    public void addCommonCards(CommonCard commonCard) {
        if(deckCommonCards.size() >= 300) {
            throw new RuntimeException("Deck can't have more than 300 cards");
        }
        deckCommonCards.add(commonCard);
    }
}
