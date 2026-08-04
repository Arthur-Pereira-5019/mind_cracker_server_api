package com.arthur_pereira.mind_cracker_server_api.mapper;

import com.arthur_pereira.mind_cracker_server_api.dto.AuthorExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.DeckExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.model.Deck;
import com.arthur_pereira.mind_cracker_server_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class DeckMapper {

    public static DeckExhibitionDTO mapDeckToDeckExhibitionDTO(Deck deck) {
        AuthorExhibitionDTO authorExhibitionDTO = UserMapper.mapUserToAuthorExhibitionDTO(deck.getAuthor());
        return new DeckExhibitionDTO(deck.getDeckId(), deck.getDeckName().getValue(), authorExhibitionDTO, deck.getDeckType().toString());
    }
}
