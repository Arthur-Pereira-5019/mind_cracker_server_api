package com.arthur_pereira.mind_cracker_server_api.controller;

import com.arthur_pereira.mind_cracker_server_api.data.board.BoardPositionType;
import com.arthur_pereira.mind_cracker_server_api.dto.board.BoardCreationDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.card.CommonCardCreationDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.common.SimpleNamedEntityDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.deck.DeckCompleteExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.deck.DeckCreationDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.deck.DeckExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.mapper.DeckMapper;
import com.arthur_pereira.mind_cracker_server_api.model.Deck;
import com.arthur_pereira.mind_cracker_server_api.service.DeckService;
import com.arthur_pereira.mind_cracker_server_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/deck")
public class DeckController {

    @Autowired
    private DeckService deckService;

    @Autowired
    private UserService userService;

    @GetMapping("/get/{id}")
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
    @PostMapping("modify/{id}/cards/add/common")
    public DeckCompleteExhibitionDTO addCommonCardToDeckById(@PathVariable("id") Long id, @RequestBody CommonCardCreationDTO commonCardCreationDTO, @AuthenticationPrincipal UserDetails userDetails) {
        Deck deck = deckService.addCardToDeck(id, userService.userFromUserDetails(userDetails), commonCardCreationDTO);
        return DeckMapper.mapToDeckCompleteExhibitionDTO(deck);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("modify/{id}/board/create/")
    public DeckCompleteExhibitionDTO associateBoardToDeckById(@PathVariable("id") Long id, @RequestBody BoardCreationDTO boardCreationDTO, @AuthenticationPrincipal UserDetails userDetails) {
        Deck deck = deckService.associateBoardToDeckById(id, userService.userFromUserDetails(userDetails), boardCreationDTO);
        return DeckMapper.mapToDeckCompleteExhibitionDTO(deck);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/modify/{id}/board/map_positions")
    public DeckCompleteExhibitionDTO associatePositionsToDeckBoard(@PathVariable("id") Long id, @RequestBody Map<Integer, BoardPositionType> boardPositions, @AuthenticationPrincipal UserDetails userDetails) {
        Deck deck = deckService.associatePositionsToDeckBoard(id, userService.userFromUserDetails(userDetails), boardPositions);
        return DeckMapper.mapToDeckCompleteExhibitionDTO(deck);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/modify/{id}/categories/add")
    public DeckCompleteExhibitionDTO addCategoryToDeck(@PathVariable("id") Long id, @RequestBody SimpleNamedEntityDTO simpleNamedEntityDTO, @AuthenticationPrincipal UserDetails userDetails) {
        Deck deck = deckService.addCategoryToDeck(id, userService.userFromUserDetails(userDetails), simpleNamedEntityDTO);
        return DeckMapper.mapToDeckCompleteExhibitionDTO(deck);
    }


    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDeckById(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        deckService.deleteDeckById(id, userService.userFromUserDetails(userDetails));
        return ResponseEntity.ok("Deck deleted with success!");
    }
}
