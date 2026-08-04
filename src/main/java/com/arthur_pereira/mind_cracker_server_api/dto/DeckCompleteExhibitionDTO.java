package com.arthur_pereira.mind_cracker_server_api.dto;

import com.arthur_pereira.mind_cracker_server_api.model.Board;

public record DeckCompleteExhibitionDTO(Long deckId,
                                        String deckName,
                                        AuthorExhibitionDTO author,
                                        String deckType,
                                        Board deckBoard
)
{
}
