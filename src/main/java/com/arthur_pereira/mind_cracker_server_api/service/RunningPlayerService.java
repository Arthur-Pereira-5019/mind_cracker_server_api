package com.arthur_pereira.mind_cracker_server_api.service;

import com.arthur_pereira.mind_cracker_server_api.model.RunningPlayer;
import com.arthur_pereira.mind_cracker_server_api.model.User;
import com.arthur_pereira.mind_cracker_server_api.repository.RunningPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RunningPlayerService {
    @Autowired
    private RunningPlayerRepository runningPlayerRepository;

    @Autowired
    private UserService userService;

    public RunningPlayer createRunningPlayer(int playerPosition, User user) {
        RunningPlayer runningPlayer = new RunningPlayer(playerPosition, user.getId(), user.getUsertag());
        return runningPlayerRepository.save(runningPlayer);
    }
}
