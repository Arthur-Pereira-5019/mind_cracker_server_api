package com.arthur_pereira.mind_cracker_server_api.mapper;

import com.arthur_pereira.mind_cracker_server_api.dto.running_player.RunningPlayerSimpleExhibitionDTOPreGame;
import com.arthur_pereira.mind_cracker_server_api.model.GenericPlayingUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunningPlayerMapper {
    public RunningPlayerSimpleExhibitionDTOPreGame genericPlayingUserSimpleExhibitionDTOPreGame(GenericPlayingUser genericPlayingUser) {
        return new RunningPlayerSimpleExhibitionDTOPreGame(genericPlayingUser.getId(), genericPlayingUser.getUsertag());
    }

    public List<RunningPlayerSimpleExhibitionDTOPreGame> genericPlayingUserSimpleExhibitionDTOPreGameList(List<? extends GenericPlayingUser> genericPlayingUsers) {
        return genericPlayingUsers.stream().map(this::genericPlayingUserSimpleExhibitionDTOPreGame).toList();
    }
}
