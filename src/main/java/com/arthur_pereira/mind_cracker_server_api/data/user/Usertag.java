package com.arthur_pereira.mind_cracker_server_api.data.user;

import com.arthur_pereira.mind_cracker_server_api.exception.common.DomainException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Usertag{
    @Column(name = "usertag",length = 128)
    private String usertag;

    public Usertag(String usertag) {
        validateUsertag(usertag);
        this.usertag = usertag;
    }

    public Usertag() {
    }

    private void validateUsertag(String usertag) {
        if (!usertag.matches(".{8,128}")) {
            throw new DomainException("Usertag must have between 8 and 128 digits!");
        }
    }

    public String getValue() {
        return usertag;
    }

    public void setValue(String usertag) {
        validateUsertag(usertag);
        this.usertag = usertag;
    }


}
