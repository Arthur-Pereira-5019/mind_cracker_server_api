package com.arthur_pereira.mind_cracker_server_api.mapper;

import com.arthur_pereira.mind_cracker_server_api.dto.AuthorExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.DeckExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.model.Deck;
import com.arthur_pereira.mind_cracker_server_api.model.User;
import org.springframework.stereotype.Service;

public class UserMapper {
    public static AuthorExhibitionDTO mapUserToAuthorExhibitionDTO(User user) {
        return new AuthorExhibitionDTO(user.getUsertag().getValue());
    }
}
