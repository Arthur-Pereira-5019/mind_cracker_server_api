package com.arthur_pereira.mind_cracker_server_api.model;


import jakarta.persistence.*;

public abstract class AbstractCard {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String cardId;

    @Column
    @ManyToOne
    private Deck associatedDeck;

    public AbstractCard(Deck associatedDeck) {
        this.associatedDeck = associatedDeck;
    }

    public Deck getAssociatedDeck() {
        return associatedDeck;
    }
}
