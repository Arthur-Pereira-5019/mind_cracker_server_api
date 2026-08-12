package com.arthur_pereira.mind_cracker_server_api.service;

import com.arthur_pereira.mind_cracker_server_api.data.common.GameName;
import com.arthur_pereira.mind_cracker_server_api.dto.card.CommonCardCreationDTO;
import com.arthur_pereira.mind_cracker_server_api.model.CardCategory;
import com.arthur_pereira.mind_cracker_server_api.model.CommonCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonCardService {
    @Autowired
    private CardCategoryService cardCategoryService;

    public CommonCard createCommonCard(CommonCardCreationDTO commonCardCreationDTO) {
        CardCategory cardCategory = null;
        if(commonCardCreationDTO.cardCategoryId() != null) {
            cardCategory = cardCategoryService.findCardCategoryById(commonCardCreationDTO.cardCategoryId());
        }
        return new CommonCard(cardCategory,
                commonCardCreationDTO.cardDifficulty(),
                new GameName(commonCardCreationDTO.cardTitle()),
                commonCardCreationDTO.tips());
    }
}
