package com.arthur_pereira.mind_cracker_server_api.dto.game;

import com.arthur_pereira.mind_cracker_server_api.data.game.ToleratedAnswerConfiguration;
import com.arthur_pereira.mind_cracker_server_api.dto.deck.DeckExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.model.RunningPlayer;

import java.util.List;

public record PreGameExhibitionDTO(Long gameId, List<RunningPlayer> players,
                                   boolean hasPassword, DeckExhibitionDTO deck,
                                   RunningPlayer conductor,
                                   ToleratedAnswerConfiguration toleratedAnswerConfiguration) {
}
