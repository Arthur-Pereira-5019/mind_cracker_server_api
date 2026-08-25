package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.common.Pair;
import com.arthur_pereira.mind_cracker_server_api.exception.common.ResourceNotFoundException;
import com.arthur_pereira.mind_cracker_server_api.mapper.GamePlayerQueueMapper;
import jakarta.persistence.*;

import java.util.*;

@Entity
public class GamePlayers {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    private Game game;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "gamePlayers"
    )
    private List<RunningPlayer> runningPlayers = new ArrayList<>();

    /**
     * For quick order manipulation on player adding, removing, querying, reversing, etc... the order system is structured by a bidirectional ring, which should be read like: "queriedPlayerId": {playerBeforeId, nextPlayerId}
     */
    @Convert(converter = GamePlayerQueueMapper.class)
    @Column(columnDefinition = "TEXT")

    private Map<Long, Pair<Long, Long>> gamePlayerQueue = new HashMap<>();

    @Column
    private Long currentPlayerId;

    @Column
    private Long lastPlayerToJoinId;

    public GamePlayers() {
    }

    /**Using currentPlayerId as the next player assumes that players can't join after the Game
     *started. As of now, it will be kept this way to spare an extra field in DB.
     * If this ever changes, just add a pointer to the first ever joined running player;
     */
    public void addRunningPlayer(RunningPlayer runningPlayer) {
        if(runningPlayers.isEmpty()) {
            currentPlayerId = runningPlayer.getId();
        } else {
            Long semiLastPlayerToJoinId = gamePlayerQueue.get(lastPlayerToJoinId).left();
            gamePlayerQueue.put(lastPlayerToJoinId,
                    new Pair<>(semiLastPlayerToJoinId, runningPlayer.getId()));
            gamePlayerQueue.put(runningPlayer.getId(),
                    new Pair<>(lastPlayerToJoinId, currentPlayerId));
        }
        runningPlayers.add(runningPlayer);
        lastPlayerToJoinId = runningPlayer.getId();
    }

    public void removeRunningPlayer(RunningPlayer runningPlayer) {
        if(!runningPlayers.isEmpty()) {
            Long removedPlayerId = runningPlayer.getId();
            Long playerBeforeId = gamePlayerQueue.get(removedPlayerId).left();
            Long playerSemiBeforeId = gamePlayerQueue.get(playerBeforeId).left();
            Long nextPlayerId = gamePlayerQueue.get(removedPlayerId).right();
            gamePlayerQueue.put(playerBeforeId,new Pair<>(playerSemiBeforeId,nextPlayerId));
            gamePlayerQueue.remove(removedPlayerId);
        }
    }

    public RunningPlayer getCurrentPlayer() {
        return runningPlayers.stream().filter(x -> Objects.equals(x.getId(), currentPlayerId)).
                findFirst().orElseThrow(() -> new ResourceNotFoundException("Unexpected behaviour of " +
                        "the Player Queue, the game must be aborted."));
    }

    public void goToNextPlayer() {
        currentPlayerId = gamePlayerQueue.get(currentPlayerId).right();
    }

    public RunningPlayer getFrontPlayer() {
        return getSortedPlayerListByScore().getFirst();
    }

    public List<RunningPlayer> getSortedPlayerListByScore() {
        return runningPlayers.stream().sorted(Comparator.comparing(RunningPlayer::getScore).reversed()).toList();
    }

    public boolean isUserAPlayer(User user) {
        return runningPlayers.stream().anyMatch(x -> x.getRelatedUserId().equals(user.getId()));
    }

    public boolean isUserCurrentPlayer(User user) {
        return getCurrentPlayer().getRelatedUserId().equals(user.getId());
    }

    public void empty() {
        runningPlayers = new ArrayList<>();
    }

    public List<RunningPlayer> getRunningPlayers() {
        return runningPlayers;
    }
}
