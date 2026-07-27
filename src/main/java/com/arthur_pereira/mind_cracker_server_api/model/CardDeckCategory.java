package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.GameName;
import jakarta.persistence.*;

@Entity
@Table
public class CardDeckCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Embedded
    private GameName name;

    @Column
    private Deck associatedDeck;


}
