package com.arthur_pereira.mind_cracker_server_api.dto.match;

import com.arthur_pereira.mind_cracker_server_api.data.deck.DeckType;

public record CreateMatchDTO(Long matchDeckId, String matchPassword, DeckType gameType) {
}
