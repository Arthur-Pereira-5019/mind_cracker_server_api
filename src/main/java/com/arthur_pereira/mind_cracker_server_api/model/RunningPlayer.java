package com.arthur_pereira.mind_cracker_server_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class RunningPlayer extends GenericPlayingUser{
    @Column
    private int roundsToSkip = 0;

    @Column
    private int score = 0;

    @ManyToOne
    private GamePlayers gamePlayers;

    public RunningPlayer() {
    }

    public RunningPlayer(User user) {
        super(user);
    }

    @Override
    public Game getAssociatedGame() {
        return gamePlayers.getGame();
    }

    public Game getCurrentGame() {
        return gamePlayers.getGame();
    }

    public GamePlayers getGamePlayers() {
        return gamePlayers;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore(int score) {
        this.score += score;
        if(score < 0) {
            score = 0;
        }
    }
    public void setGamePlayers(GamePlayers gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

}
