package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.board.BoardPositionType;
import com.arthur_pereira.mind_cracker_server_api.mapper.BoardPositionsMapper;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Entity
public class RunningGame {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long runningGameid;

    @Convert(converter = BoardPositionsMapper.class)
    @Column(columnDefinition = "TEXT")
    private Map<Integer, BoardPositionType> gameBoardPositions = new HashMap<>();

    @OneToMany
    private ArrayList<RunningPlayer> gamePlayers = new ArrayList<>();

    @Column
    private boolean hasPassword;

    @Column
    private String password;



}
