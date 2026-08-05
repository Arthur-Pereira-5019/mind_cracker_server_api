package com.arthur_pereira.mind_cracker_server_api.dto.deck;

import com.arthur_pereira.mind_cracker_server_api.data.deck.DeckType;

public record DeckCreationDTO(DeckType deckType, String deckName) {
}
