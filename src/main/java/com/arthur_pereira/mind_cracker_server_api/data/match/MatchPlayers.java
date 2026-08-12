package com.arthur_pereira.mind_cracker_server_api.data.match;

import com.arthur_pereira.mind_cracker_server_api.data.board.BoardPositionType;
import com.arthur_pereira.mind_cracker_server_api.exception.match.InexistingPlayerPosition;
import com.arthur_pereira.mind_cracker_server_api.mapper.BoardPositionsMapper;
import com.arthur_pereira.mind_cracker_server_api.mapper.MatchPlayerQueueMapper;
import com.arthur_pereira.mind_cracker_server_api.model.RunningPlayer;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Embeddable
public class MatchPlayers {
    @Column
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<RunningPlayer> matchPlayers = new ArrayList<>();

    @Convert(converter = MatchPlayerQueueMapper.class)
    @Column(columnDefinition = "TEXT")
    private Map<Integer, Long> matchPlayerQueue = new HashMap<>();

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
        if(!matchPlayerQueue.containsKey(pos) || matchPlayerQueue.get(pos) == -2L) {
            throw new InexistingPlayerPosition("Inexsting player position");
        }
        if(matchPlayerQueue.get(pos) == -1L) {
            return getPlayerAtPos(pos+1);
        }
        return getPlayerAtPos(pos);
    }

    public void incrementMatchPlayerPointer() {
        matchPlayerPointer++;
        if(matchPlayerPointer) {
            matchPlayerPointer = 0;
        }
    }

    public int count


}
