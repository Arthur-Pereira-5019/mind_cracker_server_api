package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.user.Usertag;
import jakarta.persistence.*;

import java.util.Objects;

@MappedSuperclass
public abstract class GenericPlayingUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private User originalUser;

    public GenericPlayingUser() {
    }

    public GenericPlayingUser(User originalUser) {
        this.originalUser = originalUser;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GenericPlayingUser that = (GenericPlayingUser) o;
        return Objects.equals(id, that.id) && Objects.equals(originalUser, that.originalUser);
    }

    public Long getId() {
        return id;
    }

    public User getRelatedUserId() {
        return originalUser;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originalUser.getId());
    }

    //TODO: Remove this method
    public Usertag getUsertag() {
        return originalUser.getUsertag();
    }

    public abstract Game getAssociatedGame();
}
