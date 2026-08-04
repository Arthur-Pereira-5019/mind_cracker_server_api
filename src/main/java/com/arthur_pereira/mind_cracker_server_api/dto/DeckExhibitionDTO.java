package com.arthur_pereira.mind_cracker_server_api.dto;

public record DeckExhibitionDTO(Long deckId, String deckName, AuthorExhibitionDTO author, String deckType) {
}
