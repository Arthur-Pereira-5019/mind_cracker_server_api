package com.arthur_pereira.mind_cracker_server_api.repository;

import com.arthur_pereira.mind_cracker_server_api.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
