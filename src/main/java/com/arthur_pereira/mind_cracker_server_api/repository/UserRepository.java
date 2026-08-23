package com.arthur_pereira.mind_cracker_server_api.repository;

import com.arthur_pereira.mind_cracker_server_api.data.user.Email;
import com.arthur_pereira.mind_cracker_server_api.data.user.Usertag;
import com.arthur_pereira.mind_cracker_server_api.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsertag(Usertag usertag);
    Optional<User> findByEmail(Email email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE mind_cracker.user SET playing = 0", nativeQuery = true)
    int markEveryUserAsNotPlaying();

    @Transactional
    @Modifying
    @Query(value = "UPDATE user u JOIN running_player r ON r.related_user_id = user.id JOIN " +
            "game_game_players gp ON gp.game_players_id = r.id SET c.playing = 0 WHERE " +
            "gp.game_game_id = :id;", nativeQuery = true)
    int markEveryUserOfAGameAsNotPlaying(@Param("id") Long gameId);

}
