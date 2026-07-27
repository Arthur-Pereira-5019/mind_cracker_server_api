package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.CardDifficulty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String cardId;

    @Column
    private String cardTitle;

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

    @Column
    private String cardDeckCategory;

    @Column
    private Deck associatedDeck;

    public Card(String cardDeckCategory, CardDifficulty cardDifficulty, String cardId, String cardTitle, List<String> tips, Deck deck) {
        this.cardDeckCategory = cardDeckCategory;
        this.cardDifficulty = cardDifficulty;
        this.cardId = cardId;
        this.cardTitle = cardTitle;
        this.tips = tips;
        this.associatedDeck = deck;
    }

    public String
}
