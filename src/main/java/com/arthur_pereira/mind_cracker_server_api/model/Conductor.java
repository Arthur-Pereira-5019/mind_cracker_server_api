package com.arthur_pereira.mind_cracker_server_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Conductor extends GenericPlayingUser{
    @OneToOne
    private Game associatedGame;

    public Conductor() {
        super();
    }

    public Conductor(User user) {
        super(user);
    }

    @Override
    public Game getAssociatedGame() {
        return associatedGame;
    }

    public void setAssociatedGame(Game associatedGame) {
        this.associatedGame = associatedGame;
    }
}
