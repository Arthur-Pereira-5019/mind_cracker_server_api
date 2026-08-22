package com.arthur_pereira.mind_cracker_server_api.repository;

import com.arthur_pereira.mind_cracker_server_api.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
