package com.arthur_pereira.mind_cracker_server_api.data.user;

import com.arthur_pereira.mind_cracker_server_api.exception.common.DomainException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Email{
    @Column(name = "email")
    private String email;

    public Email(String email) {
        validateEmail(email);
        this.email = email;
    }

    public Email() {
    }

    private void validateEmail(String email) {
        if(email == null || email.isBlank()) {
            throw new DomainException("Empty E-Mail");
        }
        if (!email.matches(".{3,64}\\@.{4,255}")) {
            throw new DomainException("Invalid E-Mail format!");
        }
    }

    public String getValue() {
        return email;
    }

    public void setValue(String email) {
        validateEmail(email);
        this.email = email;
    }
}
