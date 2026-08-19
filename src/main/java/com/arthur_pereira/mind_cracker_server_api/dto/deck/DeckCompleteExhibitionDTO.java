package com.arthur_pereira.mind_cracker_server_api.dto.deck;

import com.arthur_pereira.mind_cracker_server_api.data.match.ToleratedAnswerConfiguration;
import com.arthur_pereira.mind_cracker_server_api.dto.card.CommonCardSimpleExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.card.SpecialCardSimpleExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.user.AuthorExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.model.Board;
import com.arthur_pereira.mind_cracker_server_api.model.CardCategory;

import java.util.List;

public record DeckCompleteExhibitionDTO(Long deckId,
                                        String deckName,
                                        AuthorExhibitionDTO deckAuthor,
                                        String deckType,
                                        Board deckBoard,
                                        List<SpecialCardSimpleExhibitionDTO> deckSpecialCards,
                                        List<CommonCardSimpleExhibitionDTO> deckCommonCards,
                                        List<CardCategory> deckCardCategories,
                                        ToleratedAnswerConfiguration suggestedAnswerTolerance
) { }
