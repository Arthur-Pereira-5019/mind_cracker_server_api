package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.user.Usertag;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class RunningPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String relatedUserId;

    @Column
    private Usertag usertag;

    @Column
    private int roundsToSkip = 0;

    @Column
    private int score = 0;

    public RunningPlayer(String relatedUserId, Usertag usertag) {
        this.relatedUserId = relatedUserId;
        this.usertag = usertag;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RunningPlayer that = (RunningPlayer) o;
        return Objects.equals(id, that.id) && Objects.equals(relatedUserId, that.relatedUserId);
    }

    public Long getId() {
        return id;
    }

    public String getRelatedUserId() {
        return relatedUserId;
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

    @Override
    public int hashCode() {
        return Objects.hash(id, relatedUserId);
    }




}
