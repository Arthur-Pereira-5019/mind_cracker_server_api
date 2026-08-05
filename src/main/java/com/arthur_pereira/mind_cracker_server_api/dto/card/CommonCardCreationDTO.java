package com.arthur_pereira.mind_cracker_server_api.dto.card;

import com.arthur_pereira.mind_cracker_server_api.data.card.CardDifficulty;

import java.util.List;

public record CommonCardCreationDTO(String cardTitle,
                                    List<String> tips,
                                    CardDifficulty cardDifficulty,
                                    Long cardCategoryId) {
}
