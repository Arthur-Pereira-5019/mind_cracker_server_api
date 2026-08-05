package com.arthur_pereira.mind_cracker_server_api.mapper;

import com.arthur_pereira.mind_cracker_server_api.dto.deck.DeckCompleteExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.deck.DeckExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.user.AuthorExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.model.Deck;

public class DeckMapper {

    public static DeckExhibitionDTO mapToDeckExhibitionDTO(Deck deck) {
        AuthorExhibitionDTO authorExhibitionDTO = UserMapper.mapUserToAuthorExhibitionDTO(deck.getAuthor());
        return new DeckExhibitionDTO(deck.getDeckId(),
                deck.getDeckName().getValue(),
                authorExhibitionDTO,
                deck.getDeckType().toString()
        );
    }

    public static DeckCompleteExhibitionDTO mapToDeckCompleteExhibitionDTO(Deck deck) {
        AuthorExhibitionDTO authorExhibitionDTO = UserMapper.mapUserToAuthorExhibitionDTO(deck.getAuthor());
        return new DeckCompleteExhibitionDTO(deck.getDeckId(),
                deck.getDeckName().getValue(),
                authorExhibitionDTO,
                deck.getDeckType().toString(),
                deck.getBoard(),
                CardMapper.mapToListSpecialCardSimpleExhibitionDTO(deck.getDeckSpecialCards()),
                CardMapper.mapToListCommonCardSimpleExhibitionDTO(deck.getDeckCommonCards().getDeckCommonCards()),
                deck.getDeckCategories().getCardCategories()
        );
    }
}
