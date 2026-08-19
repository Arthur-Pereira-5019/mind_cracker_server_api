package com.arthur_pereira.mind_cracker_server_api.service;

import com.arthur_pereira.mind_cracker_server_api.exception.common.ResourceNotFoundException;
import com.arthur_pereira.mind_cracker_server_api.model.RunningPlayer;
import com.arthur_pereira.mind_cracker_server_api.model.User;
import com.arthur_pereira.mind_cracker_server_api.repository.RunningPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunningPlayerService {
    @Autowired
    private RunningPlayerRepository runningPlayerRepository;

    @Autowired
    private UserService userService;

    public RunningPlayer createRunningPlayer (User user) {
        RunningPlayer runningPlayer = new RunningPlayer(user.getId(), user.getUsertag());
        return runningPlayerRepository.save(runningPlayer);
    }

    public RunningPlayer makePlayerScore(int score, Long playerId) {
        RunningPlayer runningPlayer = getPlayerById(playerId);
        runningPlayer.incrementScore(score);
        return runningPlayerRepository.save(runningPlayer);
    }

    public RunningPlayer getPlayerById(Long playerId) {
        return runningPlayerRepository.findById(playerId).orElseThrow(() ->
                new ResourceNotFoundException("Couldn't find Player with the provided Id."));
    }

    public RunningPlayer findPlayerByUserId(String userId) {
        return runningPlayerRepository.findByRelatedUserId(userId).orElseThrow(() ->
                new ResourceNotFoundException("Couldn't find player with the associated User Id"));
    }
}
