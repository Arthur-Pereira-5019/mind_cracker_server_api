package com.arthur_pereira.mind_cracker_server_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.hibernate.annotations.Audited;

@Audited
@Entity
public class SpecialCard extends AbstractCard{
    @Column(length = 1024)
    private String specialCardDescription;

    /*@Column
    private String<> gameEffects*/
    //TODO: JSON Serializable string for game effects


    public SpecialCard() {
    }

    public SpecialCard(Deck associatedDeck, String specialCardDescription) {
        super(associatedDeck);
        this.specialCardDescription = specialCardDescription;
    }

    public String getSpecialCardDescription() {
        return specialCardDescription;
    }

    public void setSpecialCardDescription(String specialCardDescription) {
        this.specialCardDescription = specialCardDescription;
    }
}
