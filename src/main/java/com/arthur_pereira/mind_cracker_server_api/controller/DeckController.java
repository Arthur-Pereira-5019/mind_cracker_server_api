package com.arthur_pereira.mind_cracker_server_api.controller;

import com.arthur_pereira.mind_cracker_server_api.data.Email;
import com.arthur_pereira.mind_cracker_server_api.dto.DeckCreationDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.DeckExhibitionDTO;
import com.arthur_pereira.mind_cracker_server_api.mapper.DeckMapper;
import com.arthur_pereira.mind_cracker_server_api.model.Deck;
import com.arthur_pereira.mind_cracker_server_api.service.DeckService;
import com.arthur_pereira.mind_cracker_server_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/deck")
public class DeckController {

    @Autowired
    private DeckService deckService;

    @Autowired
    private UserService userService;
    @PostMapping("/create")
    public DeckExhibitionDTO createDeck(@RequestBody DeckCreationDTO deckCreationDTO, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Deck deck = deckService.createDeck(deckCreationDTO, userService.userFromUserDetails(userDetails));
            return DeckMapper.mapDeckToDeckExhibitionDTO(deck);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
