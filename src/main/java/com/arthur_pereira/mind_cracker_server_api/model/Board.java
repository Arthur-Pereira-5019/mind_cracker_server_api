package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.board.BoardPositionType;
import com.arthur_pereira.mind_cracker_server_api.exception.DomainException;
import com.arthur_pereira.mind_cracker_server_api.mapper.BoardPositionsMapper;
import jakarta.persistence.*;
import org.hibernate.annotations.Audited;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table
@Audited
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long boardId;

    @Column
    private boolean forcedShuffle;

    @Column
    private BoardPositionType defaultPositionType = BoardPositionType.EASY;

    @Convert(converter = BoardPositionsMapper.class)
    @Column(columnDefinition = "TEXT")
    private Map<Integer, BoardPositionType> boardPositions = new HashMap<>();

    @Column
    private int maxBoardLength;

    @ManyToOne
    @JoinColumn(name = "deck_id")
    private Deck associatedDeck;

    public Board(boolean forcedShuffle, int maxBoardLength, BoardPositionType defaultPositionType) {
        this.maxBoardLength = maxBoardLength;
        this.forcedShuffle = forcedShuffle;
        this.defaultPositionType = defaultPositionType;
    }

    public Board() {
    }

    public void setForcedShuffle(boolean forcedShuffle) {
        this.forcedShuffle = forcedShuffle;
    }

    public void setBoardLength(int boardLength) {
        boardPositions.keySet().stream().filter((x -> (x > boardLength))).
                forEach(y -> boardPositions.remove(y));
    }

    public void setBoardPositions(Map<Integer, BoardPositionType> boardPositions) {
        for(Integer i: boardPositions.keySet()) {
            if(i > maxBoardLength || i < 0) {
                throw new DomainException("One or more given positions outside of the board range of [0," + maxBoardLength+"]");
            }
        }
        this.boardPositions = boardPositions;
    }

    public void setDefaultPositionType(BoardPositionType boardPositionType) {
        this.defaultPositionType = boardPositionType;
    }

    public boolean isShuffleForced() {
        return forcedShuffle;
    }

    public int getMaxBoardLength() {
        return maxBoardLength;
    }

    public void setAssociatedDeck(Deck associatedDeck) {
        this.associatedDeck = associatedDeck;
    }
}
