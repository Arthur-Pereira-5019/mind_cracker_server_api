package com.arthur_pereira.mind_cracker_server_api.controller;

import com.arthur_pereira.mind_cracker_server_api.dto.game.CreateGameDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.game.JoinGameDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.game.PreGameExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.mapper.GameMapper;
import com.arthur_pereira.mind_cracker_server_api.model.Game;
import com.arthur_pereira.mind_cracker_server_api.model.User;
import com.arthur_pereira.mind_cracker_server_api.service.GameService;
import com.arthur_pereira.mind_cracker_server_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/game")
@RestController
public class GameController {
    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @Autowired
    private GameMapper gameMapper;

    @PostMapping("/create")
    public PreGameExhibitionDTO createGame(@RequestBody CreateGameDTO createGameDTO, @AuthenticationPrincipal User user) {
        return gameMapper.preGameExhibitionDTO(gameService.createGame(createGameDTO, user));

    }

    @PostMapping("/join")
    public PreGameExhibitionDTO joinGame(@RequestBody JoinGameDTO joinGameDTO, @AuthenticationPrincipal User user) {
        return gameMapper.preGameExhibitionDTO(gameService.joinGame(joinGameDTO, user));
    }

    @PostMapping("/leave")
    public Game leaveGame(Long gameID, @AuthenticationPrincipal User user) {
        return gameService.leaveGame(gameID, user);
    }

    @PutMapping("/next_player")
    public Game goToNextPlayer(Long matchId, @AuthenticationPrincipal User user) {
        return gameService.goToNextPlayer(matchId, user);
    }

    @PutMapping("/round/next")
    public Game goToNextRound(Long matchId, @AuthenticationPrincipal User user) {
        return gameService.nextRound(matchId, user);
    }
}
