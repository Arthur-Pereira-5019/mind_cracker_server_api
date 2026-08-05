package com.arthur_pereira.mind_cracker_server_api.controller;

import com.arthur_pereira.mind_cracker_server_api.dto.card.CommonCardCreationDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.deck.DeckCompleteExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.deck.DeckCreationDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.deck.DeckExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.mapper.DeckMapper;
import com.arthur_pereira.mind_cracker_server_api.model.Deck;
import com.arthur_pereira.mind_cracker_server_api.service.DeckService;
import com.arthur_pereira.mind_cracker_server_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/create")
    public DeckExhibitionDTO createDeck(@RequestBody DeckCreationDTO deckCreationDTO, @AuthenticationPrincipal UserDetails userDetails) {
        Deck deck = deckService.createDeck(deckCreationDTO, userService.userFromUserDetails(userDetails));
        return DeckMapper.mapToDeckExhibitionDTO(deck);
    }

    @GetMapping("find/{id}")
    public DeckCompleteExhibitionDTO findDeckById(@PathVariable("id") Long id) {
        Deck deck = deckService.findDeckById(id);
        return DeckMapper.mapToDeckCompleteExhibitionDTO(deck);
    }

    @PostMapping("/cards/add/common/{id}")
    public DeckCompleteExhibitionDTO addCommonCardToDeckById(@PathVariable("id") Long id, @RequestBody CommonCardCreationDTO commonCardCreationDTO, @AuthenticationPrincipal UserDetails userDetails) {
        Deck deck = deckService.addCardToDeck(id, userService.userFromUserDetails(userDetails), commonCardCreationDTO);
        return DeckMapper.mapToDeckCompleteExhibitionDTO(deck);

    }

}
