package com.arthur_pereira.mind_cracker_server_api.data.card;

import com.arthur_pereira.mind_cracker_server_api.exception.common.DomainException;
import com.arthur_pereira.mind_cracker_server_api.exception.common.ResourceNotFoundException;
import com.arthur_pereira.mind_cracker_server_api.model.CardCategory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Embeddable
public class CardCategoriesList {

    @Column
    @OneToMany(
            mappedBy = "associatedDeck",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<CardCategory> cardCategories = new ArrayList<>();

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

    public List<CardCategory> getCardCategories() {
        return cardCategories;
    }
}
