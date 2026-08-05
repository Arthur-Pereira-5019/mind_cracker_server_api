package com.arthur_pereira.mind_cracker_server_api.data.user;

import com.arthur_pereira.mind_cracker_server_api.exception.DomainException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Embeddable
public class Password {
    @Column(name = "password")
    private String password;

    public Password(String password) {
        validatePassword(password);
        this.password = encrypt(password);
    }

    public Password() {
    }

    public void validatePassword(String password) {
        if(!(password.matches(".*[a-z].*|.*[a-z]|[a-z].*"))) {
            throw new DomainException("Password must contain at least one lowercase character!");
        }
        if(!(password.matches(".*[A-Z].*|.*[A-Z]|[A-Z].*"))) {
            throw new DomainException("Password must contain at least one uppercase character!");
        }
        if(!(password.matches(".*[0-9].*|.*[0-9]|[0-9].*"))) {
            throw new DomainException("Password must contain at least one special number!");
        }
        if(!(password.matches(".*\\W.*|.*\\W|\\W.*"))) {
            throw new DomainException("Password must contain at least one special character!");
        }
        if (!password.matches(".{8,128}")) {
            throw new DomainException("Password must have between 8 and 128 digits!");
        }
    }

    public String getValue() {
        return password;
    }

    public void setValue(String password) {
        validatePassword(password);
        this.password = encrypt(password);
    }

    public String encrypt(String rawPassword) {
        return new BCryptPasswordEncoder().encode(rawPassword);
    }
}
