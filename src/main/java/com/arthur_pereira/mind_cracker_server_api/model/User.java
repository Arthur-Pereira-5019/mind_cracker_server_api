package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.user.Email;
import com.arthur_pereira.mind_cracker_server_api.data.user.Password;
import com.arthur_pereira.mind_cracker_server_api.data.user.UserRole;
import com.arthur_pereira.mind_cracker_server_api.data.user.Usertag;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

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

    public Usertag getUsertag() {
        return usertag;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority admin = new SimpleGrantedAuthority("ROLE_ADMIN");
        SimpleGrantedAuthority moderator = new SimpleGrantedAuthority("ROLE_MODERATOR");
        SimpleGrantedAuthority user = new SimpleGrantedAuthority("ROLE_USER");
        SimpleGrantedAuthority suspended = new SimpleGrantedAuthority("ROLE_SUSPENDED");
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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User) {
            if(Objects.equals(((User) obj).id, this.id)) {
                return true;
            }
        }
        return false;
    }}
