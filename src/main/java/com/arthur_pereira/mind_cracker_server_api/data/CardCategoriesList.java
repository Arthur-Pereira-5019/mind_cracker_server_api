package com.arthur_pereira.mind_cracker_server_api.data;

import com.arthur_pereira.mind_cracker_server_api.exception.DomainException;
import com.arthur_pereira.mind_cracker_server_api.exception.ResourceNotFoundException;
import com.arthur_pereira.mind_cracker_server_api.model.CardCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.Objects;

@Embeddable
public class CardCategoriesList {

    @Column
    @OneToMany(orphanRemoval = true)
    private ArrayList<CardCategory> cardCategories = new ArrayList<>();

    public CardCategoriesList() {
    }

    public void addCardCategory(CardCategory cardCategory) {
        if(cardCategories.size() < 10) {
            cardCategories.add(cardCategory);
        } else {
            throw new DomainException("Deck's cant have more than 10 CommonCard Categories");
        }
    }

    public void removeCardCategory(Long id) {
        CardCategory cardCategory = cardCategories.stream().
                filter(x -> Objects.equals(x.getId(), id)).findFirst().
                orElseThrow(() -> new ResourceNotFoundException("Couldn't find CommonCard Category with the given Id."));
        cardCategories.remove(cardCategory);
    }

    public ArrayList<CardCategory> getCardCategories() {
        return cardCategories;
    }
}
