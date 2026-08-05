package com.arthur_pereira.mind_cracker_server_api.service;

import com.arthur_pereira.mind_cracker_server_api.exception.ResourceNotFoundException;
import com.arthur_pereira.mind_cracker_server_api.model.CardCategory;
import com.arthur_pereira.mind_cracker_server_api.repository.CardCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardCategoryService {
    @Autowired
    private CardCategoryRepository cardCategoryRepository;

    public CardCategory findCardCategoryById(Long id) {
        return cardCategoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Couldn't find Card Category with the provided Id."));
    }
}
