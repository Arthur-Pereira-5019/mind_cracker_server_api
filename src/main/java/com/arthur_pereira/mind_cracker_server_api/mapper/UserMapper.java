package com.arthur_pereira.mind_cracker_server_api.mapper;

import com.arthur_pereira.mind_cracker_server_api.dto.user.AuthorExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.model.User;

public class UserMapper {
    public static AuthorExhibitionDTO mapUserToAuthorExhibitionDTO(User user) {
        return new AuthorExhibitionDTO(user.getUsertag().getValue());
    }
}
