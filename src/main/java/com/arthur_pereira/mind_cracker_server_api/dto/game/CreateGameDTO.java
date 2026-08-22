package com.arthur_pereira.mind_cracker_server_api.dto.game;

import com.arthur_pereira.mind_cracker_server_api.data.deck.DeckType;
import com.arthur_pereira.mind_cracker_server_api.data.game.ToleratedAnswerConfiguration;

public record CreateGameDTO(Long gameDeckId,
                             String gamePassword,
                             DeckType gameType,
                             ToleratedAnswerConfiguration toleratedAnswerConfiguration) {
}
