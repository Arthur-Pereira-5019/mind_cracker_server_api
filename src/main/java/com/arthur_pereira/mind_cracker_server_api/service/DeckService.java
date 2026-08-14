package com.arthur_pereira.mind_cracker_server_api.service;

import com.arthur_pereira.mind_cracker_server_api.data.board.BoardPositionType;
import com.arthur_pereira.mind_cracker_server_api.data.card.CardCategoriesList;
import com.arthur_pereira.mind_cracker_server_api.data.common.GameName;
import com.arthur_pereira.mind_cracker_server_api.dto.board.BoardCreationDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.card.CommonCardCreationDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.common.SimpleNamedEntityDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.deck.DeckCreationDTO;
import com.arthur_pereira.mind_cracker_server_api.exception.common.ResourceNotFoundException;
import com.arthur_pereira.mind_cracker_server_api.exception.security.UnauthorizedActionException;
import com.arthur_pereira.mind_cracker_server_api.model.*;
import com.arthur_pereira.mind_cracker_server_api.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class DeckService {
    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private CardCategoryService cardCategoryService;

    @Autowired
    private CommonCardService commonCardService;

    public Deck createDeck(DeckCreationDTO deckCreationDTO, User deckAuthor) {
        GameName deckName = new GameName(deckCreationDTO.deckName());
        Deck deck = new Deck(deckAuthor, deckCreationDTO.deckType(), deckName);
        return deckRepository.save(deck);
    }

    public Deck addCategoryToDeck(Long id, User user, SimpleNamedEntityDTO simpleNamedEntityDTO) {
        Deck deck = getDeckForUpdate(id, user);
        CardCategoriesList cardCategoriesList = deck.getDeckCategories();
        cardCategoriesList.addCardCategory(new CardCategory(new GameName(simpleNamedEntityDTO.name())));
        deck.setDeckCategories(cardCategoriesList);
        return deckRepository.save(deck);
    }

    public Deck addCardToDeck(Long id, User user, CommonCardCreationDTO commonCardCreationDTO) {
        Deck deck = getDeckForUpdate(id, user);
        CommonCard commonCard = commonCardService.createCommonCard(commonCardCreationDTO);
        deck.getDeckCommonCards().addCommonCards(commonCard, deck);
        return deckRepository.save(deck);
    }

    public Deck associateBoardToDeckById(Long id, User user, BoardCreationDTO boardCreationDTO) {
        Deck deck = getDeckForUpdate(id, user);
        Board board = new Board(boardCreationDTO.forcedShuffle(),
                boardCreationDTO.maxBoardLength(),
                boardCreationDTO.defaultPositionType()
        );
        deck.associateBoard(board);
        return deckRepository.save(deck);
    }

    public Deck associatePositionsToDeckBoard(Long id, User user, Map<Integer, BoardPositionType> boardPositions) {
        Deck deck = getDeckForUpdate(id, user);
        Board board = deck.getBoard();
        board.setBoardPositions(boardPositions);
        deck.associateBoard(board);
        return deckRepository.save(deck);
    }

    public void deleteDeckById(Long id, User user) {
        Deck deck = getDeckForUpdate(id, user);
        deckRepository.delete(deck);
    }

    public Deck findDeckById(Long id) {
        return deckRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Couldn't find Deck with the given Id."));
    }

    public Deck getDeckForUpdate(Long id, User user) {
        Deck deck = findDeckById(id);
        havePowerOverDeck(deck, user);
        return deck;
    }

    public void updateDeckName(Long id, String name, User user) {
        Deck deck = getDeckForUpdate(id, user);
        GameName deckName = new GameName(name);
        deck.setDeckName(deckName);
        deckRepository.save(deck);
    }

    public void havePowerOverDeck(Deck deck, User user) {
        if(!user.equals(deck.getAuthor())) {
            user.getAuthorities().forEach(x -> {
                if(Objects.equals(x.getAuthority(), "MODERATOR")) {
                    return;
                }
            });
            throw new UnauthorizedActionException("User is not owner of the Deck nor an Moderator.");
        }
    }
}
