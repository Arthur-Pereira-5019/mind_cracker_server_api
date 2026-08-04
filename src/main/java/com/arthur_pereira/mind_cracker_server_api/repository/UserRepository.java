package com.arthur_pereira.mind_cracker_server_api.repository;

import com.arthur_pereira.mind_cracker_server_api.data.Email;
import com.arthur_pereira.mind_cracker_server_api.data.Usertag;
import com.arthur_pereira.mind_cracker_server_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsertag(Usertag usertag);
    Optional<User> findByEmail(Email email);
}
