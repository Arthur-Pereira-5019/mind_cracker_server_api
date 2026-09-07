package com.arthur_pereira.mind_cracker_server_api.dto.game;

import com.arthur_pereira.mind_cracker_server_api.data.game.ToleratedAnswerConfiguration;
import com.arthur_pereira.mind_cracker_server_api.dto.deck.DeckExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.running_player.RunningPlayerSimpleExhibitionDTOPreGame;

import java.util.List;

public record PreGameExhibitionDTO(Long gameId,
                                   List<RunningPlayerSimpleExhibitionDTOPreGame> players,
                                   boolean hasPassword,
                                   DeckExhibitionDTO deck,
                                   RunningPlayerSimpleExhibitionDTOPreGame conductor,
                                   ToleratedAnswerConfiguration toleratedAnswerConfiguration) {
}
