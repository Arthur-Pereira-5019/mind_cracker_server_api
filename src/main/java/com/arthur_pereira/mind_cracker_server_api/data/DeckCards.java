package com.arthur_pereira.mind_cracker_server_api.data;

import com.arthur_pereira.mind_cracker_server_api.model.AbstractCard;
import com.arthur_pereira.mind_cracker_server_api.model.CommonCard;
import com.arthur_pereira.mind_cracker_server_api.model.SpecialCard;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class DeckCards {

    @Column
    @OneToMany(orphanRemoval = true)
    private ArrayList<CommonCard> commonCards = new ArrayList<>();

    @Column
    @OneToMany(orphanRemoval = true)
    private ArrayList<SpecialCard> deckSpecialCards = new ArrayList<>();

    public DeckCards(ArrayList<CommonCard> commonCards, ArrayList<SpecialCard> specialCards) {
        this.commonCards = commonCards;
        this.deckSpecialCards = specialCards;
    }

    public DeckCards() {
    }

    public void addCommonCardToDeck(CommonCard commonCard) {
        commonCards.add(commonCard);
    }

    public void addSpecialCardToDeck(SpecialCard specialCard) {
        deckSpecialCards.add(specialCard);
    }

    public void addSpecialCardsToDeck(List<SpecialCard> specialCards) {
        deckSpecialCards.addAll(specialCards);
    }

    public void addCommonCardsToDeck(List<CommonCard> commonCards) {
        this.commonCards.addAll(commonCards);
    }

    public boolean hasEnoughDefaultCards(int n) {
        int found = 0;
        for(AbstractCard abstractCard: commonCards) {
            if(abstractCard instanceof CommonCard) {
                found += 1;
                if(found == n) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<CommonCard> getCommonCards() {
        return commonCards;
    }

    public ArrayList<SpecialCard> getSpecialCards() {
        return deckSpecialCards;
    }

}
