package com.arthur_pereira.mind_cracker_server_api.mapper;

import com.arthur_pereira.mind_cracker_server_api.dto.card.CommonCardSimpleExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.card.SpecialCardSimpleExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.model.CommonCard;
import com.arthur_pereira.mind_cracker_server_api.model.SpecialCard;

import java.util.ArrayList;
import java.util.List;

public class CardMapper {
    public static List<CommonCardSimpleExhibitionDTO> mapToListCommonCardSimpleExhibitionDTO(List<CommonCard> commonCards) {
        ArrayList<CommonCardSimpleExhibitionDTO> commonCardSimpleExhibitionDTOS = new ArrayList<>();
        for (CommonCard commonCard : commonCards) {
            commonCardSimpleExhibitionDTOS.add(new CommonCardSimpleExhibitionDTO(commonCard.getCardId(),
                    commonCard.getCardTitle().getValue(),
                    commonCard.getCardTips().getTips(),
                    commonCard.getCardDifficulty(),
                    commonCard.getCardDeckCategory() == null ? "null" : commonCard.getCardDeckCategory().getName().getValue()
            ));
        }
        return commonCardSimpleExhibitionDTOS;
    }

    public static List<SpecialCardSimpleExhibitionDTO> mapToListSpecialCardSimpleExhibitionDTO(List<SpecialCard> specialCards) {
        ArrayList<SpecialCardSimpleExhibitionDTO> specialCardSimpleExhibitionDTOS = new ArrayList<>();
        for (SpecialCard specialCard : specialCards) {
            specialCardSimpleExhibitionDTOS.add(new SpecialCardSimpleExhibitionDTO(specialCard.getCardId(),
                    specialCard.getSpecialCardDescription()
            ));
        }
        return specialCardSimpleExhibitionDTOS;
    }
}
