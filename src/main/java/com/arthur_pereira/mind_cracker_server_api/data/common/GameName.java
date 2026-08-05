package com.arthur_pereira.mind_cracker_server_api.data.common;

import com.arthur_pereira.mind_cracker_server_api.exception.DomainException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class GameName{
    @Column(name = "name")
    private String gameName;

    public GameName(String gameName) {
        validateGameName(gameName);
        this.gameName = gameName;
    }

    public GameName() {
    }

    private void validateGameName(String gameName) {
        if (!gameName.matches(".{3,255}")) {
            throw new DomainException("Name must have between 8 and 255 digits!");
        }
    }

    public String getValue() {
        return gameName;
    }

    public void setValue(String gameName) {
        validateGameName(gameName);
        this.gameName = gameName;
    }


}
