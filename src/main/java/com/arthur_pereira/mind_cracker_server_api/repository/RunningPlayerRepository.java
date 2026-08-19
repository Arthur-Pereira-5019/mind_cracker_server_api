package com.arthur_pereira.mind_cracker_server_api.repository;

import com.arthur_pereira.mind_cracker_server_api.model.RunningPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RunningPlayerRepository extends JpaRepository<RunningPlayer, Long> {
    public Optional<RunningPlayer> findByRelatedUserId(String relatedUserId);
}
