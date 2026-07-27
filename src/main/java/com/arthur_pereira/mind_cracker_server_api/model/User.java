package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.Email;
import com.arthur_pereira.mind_cracker_server_api.data.Password;
import com.arthur_pereira.mind_cracker_server_api.data.Usertag;
import jakarta.persistence.*;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Embedded
    private Usertag usertag;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Column
    private boolean playing = false;

    public User(Email email, Password password, Usertag usertag) {
        this.email = email;
        this.password = password;
        this.usertag = usertag;
    }

    public void togglePlayingState() {
        playing = !playing;
    }

    public String getUsertag() {
        return usertag.getValue();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return password.getValue();
    }

    @Override
    public String getUsername() {
        return email.getValue();
    }


}
