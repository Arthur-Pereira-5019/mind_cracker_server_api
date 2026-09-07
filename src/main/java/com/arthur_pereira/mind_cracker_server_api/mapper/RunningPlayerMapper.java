package com.arthur_pereira.mind_cracker_server_api.mapper;

import com.arthur_pereira.mind_cracker_server_api.dto.running_player.RunningPlayerSimpleExhibitionDTOPreGame;
import com.arthur_pereira.mind_cracker_server_api.model.RunningPlayer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunningPlayerMapper {
    public RunningPlayerSimpleExhibitionDTOPreGame runningPlayerSimpleExhibitionDTO(RunningPlayer runningPlayer) {
        return new RunningPlayerSimpleExhibitionDTOPreGame(runningPlayer.getId(), runningPlayer.getUsertag());
    }

    public List<RunningPlayerSimpleExhibitionDTOPreGame> runningPlayerSimpleExhibitionDTOPreGameList(List<RunningPlayer> runningPlayers) {
        return runningPlayers.stream().map(this::runningPlayerSimpleExhibitionDTO).toList();
    }
}
