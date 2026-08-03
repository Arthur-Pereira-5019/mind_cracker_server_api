package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.Email;
import com.arthur_pereira.mind_cracker_server_api.data.Password;
import com.arthur_pereira.mind_cracker_server_api.data.UserRole;
import com.arthur_pereira.mind_cracker_server_api.data.Usertag;
import jakarta.persistence.*;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    @Column(unique = true)
    private Usertag usertag;

    @Embedded
    @Column(unique = true)
    private Email email;

    @Embedded
    private Password password;

    @Column
    private boolean playing = false;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole = UserRole.USER;

    public User() {
    }

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
        SimpleGrantedAuthority admin = new SimpleGrantedAuthority("ADMIN");
        SimpleGrantedAuthority moderator = new SimpleGrantedAuthority("MODERATOR");
        SimpleGrantedAuthority user = new SimpleGrantedAuthority("USER");
        SimpleGrantedAuthority suspended = new SimpleGrantedAuthority("SUSPENDED");
        switch (userRole) {
            case ADMIN -> {
                return List.of(admin, moderator, user);
            }
            case MODERATOR -> {
                return List.of(moderator, user);
            }
            case SUSPENDED -> {
                return List.of(suspended);
            }
            default -> {
                return List.of(user);
            }
        }
    }

    @Override
    public @Nullable String getPassword() {
        return password.getValue();
    }

    @Override
    public String getUsername() {
        return email.getValue();
    }

    public String getId() {
        return id;
    }

    public void promoteTo(UserRole userRole) {
        this.userRole = userRole;
    }

}
