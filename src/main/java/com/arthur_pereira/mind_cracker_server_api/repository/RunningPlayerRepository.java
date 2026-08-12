package com.arthur_pereira.mind_cracker_server_api.repository;

import com.arthur_pereira.mind_cracker_server_api.model.RunningPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunningPlayerRepository extends JpaRepository<RunningPlayer, Long> {
}
