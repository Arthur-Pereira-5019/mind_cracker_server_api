package com.arthur_pereira.mind_cracker_server_api.controller;

import com.arthur_pereira.mind_cracker_server_api.dto.match.CreateMatchDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.match.JoinMatchDTO;
import com.arthur_pereira.mind_cracker_server_api.model.Match;
import com.arthur_pereira.mind_cracker_server_api.model.User;
import com.arthur_pereira.mind_cracker_server_api.service.MatchService;
import com.arthur_pereira.mind_cracker_server_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/match")
public class MatchController {
    @Autowired
    private MatchService matchService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public Match createMatch(@RequestBody CreateMatchDTO createMatchDTO, @AuthenticationPrincipal User user) {
        return matchService.createMatch(createMatchDTO, user);
    }

    @PostMapping("/join")
    public Match joinMatch(@RequestBody JoinMatchDTO joinMatchDTO, @AuthenticationPrincipal User user) {
        return matchService.joinMatch(joinMatchDTO, user);
    }

    @PostMapping("/leave")
    public Match leaveMatch(Long matchInt, @AuthenticationPrincipal User user) {
        return matchService.leaveMatch(matchInt, user);
    }
}
