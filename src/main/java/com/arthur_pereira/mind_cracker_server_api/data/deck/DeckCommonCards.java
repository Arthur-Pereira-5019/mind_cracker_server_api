package com.arthur_pereira.mind_cracker_server_api.data.deck;

import com.arthur_pereira.mind_cracker_server_api.data.card.CardDifficulty;
import com.arthur_pereira.mind_cracker_server_api.model.CommonCard;
import com.arthur_pereira.mind_cracker_server_api.model.Deck;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public void addCommonCards(CommonCard commonCard, Deck ownerDeck) {
        if(deckCommonCards.size() >= 300) {
            throw new RuntimeException("Deck can't have more than 300 cards");
        }
        commonCard.setAssociatedDeck(ownerDeck);
        deckCommonCards.add(commonCard);
    }

    //TODO: ASK THE CONDUCTOR WHAT TO DO ONCE NOT ENOUGH CARDS
    public CommonCard shuffleCommonCardOfType(CardDifficulty cardDifficulty, List<Long> usedCardsIds) {
        Random random = new Random();
        List<CommonCard> filteredCards = filterAwayUsedCards(findCommonCardsOfType(cardDifficulty),
                usedCardsIds);
        return filteredCards.get(random.nextInt(0,filteredCards.size()));
    }

    private List<CommonCard> filterAwayUsedCards(List<CommonCard> commonCards, List<Long> usedCardIds) {
        return commonCards.stream().filter(x -> !usedCardIds.contains(x.getCardId())).toList();
    }

    public List<CommonCard> findCommonCardsOfType(CardDifficulty cardDifficulty) {
        return deckCommonCards.stream().filter(x ->
                x.getCardDifficulty() == cardDifficulty).toList();
    }
}
