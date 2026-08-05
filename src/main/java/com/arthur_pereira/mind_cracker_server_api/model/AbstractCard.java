package com.arthur_pereira.mind_cracker_server_api.model;


import jakarta.persistence.*;

@MappedSuperclass
public abstract class AbstractCard {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long cardId;

    @ManyToOne
    private Deck associatedDeck;

    public AbstractCard(Deck associatedDeck) {
        this.associatedDeck = associatedDeck;
    }

    public Deck getAssociatedDeck() {
        return associatedDeck;
    }

    public AbstractCard() {
    }

    public Long getCardId() {
        return cardId;
    }
}
