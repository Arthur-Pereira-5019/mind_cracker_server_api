package com.arthur_pereira.mind_cracker_server_api.dto.deck;

import com.arthur_pereira.mind_cracker_server_api.dto.user.AuthorExhibitionDTO;
public record DeckExhibitionDTO(Long deckId, String deckName, AuthorExhibitionDTO author, String deckType) {
}
