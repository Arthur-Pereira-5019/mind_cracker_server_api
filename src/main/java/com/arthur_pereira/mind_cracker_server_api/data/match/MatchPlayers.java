package com.arthur_pereira.mind_cracker_server_api.data.match;

import com.arthur_pereira.mind_cracker_server_api.data.common.Pair;
import com.arthur_pereira.mind_cracker_server_api.exception.common.ResourceNotFoundException;
import com.arthur_pereira.mind_cracker_server_api.mapper.MatchPlayerQueueMapper;
import com.arthur_pereira.mind_cracker_server_api.model.RunningPlayer;
import jakarta.persistence.*;

import java.util.*;

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
    /*For quick order manipulation on player adding, removing, querying, reversing, etc... this structure
    Is a bidirectional ring, so it should be read like: "queriedPlayerId": {playerBeforeId, nextPlayerId}*/
    private Map<Long, Pair<Long, Long>> matchPlayerQueue = new HashMap<>();

    @Column
    private Long currentPlayerId;

    @Column
    private Long lastPlayerToJoinId;

    public MatchPlayers() {
    }

    /*Using currentPlayerId as the next player assumes that players can't join after the Match
     started. As of now, it will be kept this way to spare an extra field in DB.
     If this ever changes, just add a pointer to the first ever joined running player;*/
    public void addRunningPlayer(RunningPlayer runningPlayer) {
        if(matchPlayers.isEmpty()) {
            currentPlayerId = runningPlayer.getId();
        } else {
            Long semiLastPlayerToJoinId = matchPlayerQueue.get(lastPlayerToJoinId).left();
            matchPlayerQueue.put(lastPlayerToJoinId,
                    new Pair<>(semiLastPlayerToJoinId, runningPlayer.getId()));
            matchPlayerQueue.put(runningPlayer.getId(),
                    new Pair<>(lastPlayerToJoinId, currentPlayerId));
        }
        matchPlayers.add(runningPlayer);
        lastPlayerToJoinId = runningPlayer.getId();
    }

    public void removeRunningPlayer(RunningPlayer runningPlayer) {
        if(!matchPlayers.isEmpty()) {
            Long removedPlayerId = runningPlayer.getId();
            Long playerBeforeId = matchPlayerQueue.get(removedPlayerId).left();
            Long playerSemiBeforeId = matchPlayerQueue.get(playerBeforeId).left();
            Long nextPlayerId = matchPlayerQueue.get(removedPlayerId).right();
            matchPlayerQueue.put(playerBeforeId,new Pair<>(playerSemiBeforeId,nextPlayerId));
            matchPlayerQueue.remove(removedPlayerId);
        }
    }

    public RunningPlayer getCurrentPlayer() {
        return matchPlayers.stream().filter(x -> Objects.equals(x.getId(), currentPlayerId)).
                findFirst().orElseThrow(() -> new ResourceNotFoundException("Unexpected behaviour of" +
                        "the Player Queue, the game must be aborted."));
    }

    public void goToNextPlayer() {
        currentPlayerId = matchPlayerQueue.get(currentPlayerId).right();
    }

    public RunningPlayer getFrontPlayer() {
        return getSortedPlayerListByScore().getFirst();
    }

    public List<RunningPlayer> getSortedPlayerListByScore() {
        return matchPlayers.stream().sorted(Comparator.comparing(RunningPlayer::getScore).reversed()).toList();
    }




}
