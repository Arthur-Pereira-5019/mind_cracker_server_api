package com.arthur_pereira.mind_cracker_server_api.mapper;

import com.arthur_pereira.mind_cracker_server_api.dto.game.PreGameExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameMapper {

    @Autowired
    private DeckMapper deckMapper;

    @Autowired
    private RunningPlayerMapper runningPlayerMapper;

    public PreGameExhibitionDTO preGameExhibitionDTO(Game game) {
        return new PreGameExhibitionDTO(
                game.getGameId(),
                runningPlayerMapper.runningPlayerSimpleExhibitionDTOPreGameList(game.getGamePlayers().generatePartialOrder()),
                game.isStarted(),
                deckMapper.mapToDeckExhibitionDTO(game.getGameDeck()),
                runningPlayerMapper.runningPlayerSimpleExhibitionDTO(game.getGameConductor()),
                game.getToleratedAnswerConfiguration()
        );
    }

}
