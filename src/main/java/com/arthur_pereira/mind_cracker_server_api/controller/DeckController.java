package com.arthur_pereira.mind_cracker_server_api.controller;

import com.arthur_pereira.mind_cracker_server_api.dto.board.BoardCreationDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.card.CommonCardCreationDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.deck.DeckCompleteExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.deck.DeckCreationDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.deck.DeckExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.mapper.DeckMapper;
import com.arthur_pereira.mind_cracker_server_api.model.Deck;
import com.arthur_pereira.mind_cracker_server_api.service.DeckService;
import com.arthur_pereira.mind_cracker_server_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deck")
public class DeckController {

    @Autowired
    private DeckService deckService;

    @Autowired
    private UserService userService;

    @GetMapping("find/{id}")
    public DeckCompleteExhibitionDTO findDeckById(@PathVariable("id") Long id) {
        Deck deck = deckService.findDeckById(id);
        return DeckMapper.mapToDeckCompleteExhibitionDTO(deck);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public DeckExhibitionDTO createDeck(@RequestBody DeckCreationDTO deckCreationDTO, @AuthenticationPrincipal UserDetails userDetails) {
        Deck deck = deckService.createDeck(deckCreationDTO, userService.userFromUserDetails(userDetails));
        return DeckMapper.mapToDeckExhibitionDTO(deck);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/cards/add/common/{id}")
    public DeckCompleteExhibitionDTO addCommonCardToDeckById(@PathVariable("id") Long id, @RequestBody CommonCardCreationDTO commonCardCreationDTO, @AuthenticationPrincipal UserDetails userDetails) {
        Deck deck = deckService.addCardToDeck(id, userService.userFromUserDetails(userDetails), commonCardCreationDTO);
        return DeckMapper.mapToDeckCompleteExhibitionDTO(deck);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/board/create/{id}")
    public DeckCompleteExhibitionDTO associateBoardToDeckById(@PathVariable("id") Long id, @RequestBody BoardCreationDTO boardCreationDTO, @AuthenticationPrincipal UserDetails userDetails) {
        Deck deck = deckService.associateBoardToDeckById(id, userService.userFromUserDetails(userDetails), boardCreationDTO);
        return DeckMapper.mapToDeckCompleteExhibitionDTO(deck);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/board/positions/{id}")
    public DeckCompleteExhibitionDTO associatePositionsToDeckBoard(@PathVariable("id") Long id, @RequestBody BoardCreationDTO boardCreationDTO, @AuthenticationPrincipal UserDetails userDetails) {
        Deck deck = deckService.associateBoardToDeckById(id, userService.userFromUserDetails(userDetails), boardCreationDTO);
        return DeckMapper.mapToDeckCompleteExhibitionDTO(deck);
    }
}
