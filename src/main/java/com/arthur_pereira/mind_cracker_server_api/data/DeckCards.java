package com.arthur_pereira.mind_cracker_server_api.data;

import com.arthur_pereira.mind_cracker_server_api.model.AbstractCard;
import com.arthur_pereira.mind_cracker_server_api.model.Card;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class DeckCards {
    @Column
    @OneToMany
    private ArrayList<AbstractCard> deckCards = new ArrayList<>();

    public DeckCards(ArrayList<AbstractCard> deckCards) {
        this.deckCards = deckCards;
    }

    public DeckCards() {
    }

    public void addCardToDeck(AbstractCard abstractCard) {
        deckCards.add(abstractCard);
    }

    public void addCardsToDeck(List<AbstractCard> abstractCardList) {
        deckCards.addAll(abstractCardList);
    }

    public boolean hasEnoughDefaultCards(int n) {
        int found = 0;
        for(AbstractCard abstractCard: deckCards) {
            if(abstractCard instanceof Card) {
                found += 1;
                if(found == n) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<AbstractCard> getValues() {
        return deckCards;
    }

}
