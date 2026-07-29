package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.GameName;
import jakarta.persistence.*;

@Entity
@Table
public class CardCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Embedded
    private GameName name;

    @OneToOne
    private Deck associatedDeck;

    public CardCategory(GameName name) {
        this.name = name;
    }

    public GameName getName() {
        return name;
    }

    public Deck getAssociatedDeck() {
        return associatedDeck;
    }

    public void setName(GameName name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
}
