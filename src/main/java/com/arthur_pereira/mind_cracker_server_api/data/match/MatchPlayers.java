package com.arthur_pereira.mind_cracker_server_api.data.match;

import com.arthur_pereira.mind_cracker_server_api.model.RunningPlayer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class MatchPlayers {
    @Column
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<RunningPlayer> matchPlayers = new ArrayList<>();

    @Column
    private int matchPlayerPointer = 0;

    public MatchPlayers() {
    }

    public void addRunningPlayer(RunningPlayer runningPlayer) {
        matchPlayers.add(runningPlayer);
    }

    public void removeRunningPlayer(RunningPlayer runningPlayer, boolean fixOrder) {
        matchPlayers.remove(runningPlayer);
    }

    public RunningPlayer getPlayerAtPos(int pos) {

    }

    public void incrementMatchPlayerPointer() {
        matchPlayerPointer++;
        if(matchPlayerPointer == matchPlayers.size()-2) {
            matchPlayerPointer = 0;
        }
    }


}
