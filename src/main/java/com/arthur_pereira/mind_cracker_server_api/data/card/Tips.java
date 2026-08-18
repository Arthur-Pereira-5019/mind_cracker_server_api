package com.arthur_pereira.mind_cracker_server_api.data.card;

import com.arthur_pereira.mind_cracker_server_api.exception.common.DomainException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Tips {
    @ElementCollection
    @CollectionTable(
            name = "card_tips",
            joinColumns = @JoinColumn(name = "card_id")
    )
    @Column(name = "tip")
    private List<String> tips = new ArrayList<>();

    public int getNumberOfTips() {
        return tips.size();
    }

    public Tips(List<String> tips) {
        validateTips(tips);
        this.tips = tips;
    }

    private void validateTips(List<String> tips) {
        for (String tip : tips) {
            if(tip.length() > 255) {
                throw new DomainException("Tips should have at last only 255 characters!");
            }
        }
        if(tips.size() > 25) {
            throw new DomainException("Each Common Card can only have 25 tips!");
        }
        if(tips.isEmpty()) {
            throw new DomainException("Add at least one tip String to the list");
        }
    }

    public List<String> getTips() {
        return tips;
    }

    public String getTipAtPosWithCypher(int position, int cypher) {
        return tips.get(cypheredPosition(position, cypher));
    }

    public List<String> getUsedTips(List<Integer> positions, int cypher) {
        ArrayList<String> usedTips = new ArrayList<>();
        for (Integer position : positions) {
            usedTips.add(tips.get(cypheredPosition(position,cypher)));
        }
        return usedTips;
    }

    private int cypheredPosition(int position, int cypher) {
        return (position + cypher) % getNumberOfTips();
    }

}
