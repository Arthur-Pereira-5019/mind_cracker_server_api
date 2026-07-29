package com.arthur_pereira.mind_cracker_server_api.dto;

import com.arthur_pereira.mind_cracker_server_api.data.DeckType;

import java.util.ArrayList;

public record DeckCreationDTO(DeckType deckType, String deckName) {
}
